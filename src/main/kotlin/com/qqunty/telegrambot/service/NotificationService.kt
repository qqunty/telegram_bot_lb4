package com.qqunty.telegrambot.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.qqunty.telegrambot.bot.NotificationBot
import com.qqunty.telegrambot.domain.*
import com.qqunty.telegrambot.repository.*
import com.qqunty.telegrambot.web.dto.ScheduleDto
import org.quartz.Scheduler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * 1. Мгновенные уведомления (из веб-хук или команд бота)
 * 2. Создание отложенных Quartz-задач
 * 3. Отправка отложенных уведомлений (Quartz вызывает sendScheduled)
 */
@Service
class NotificationService(
    private val templateRepo: TemplateRepository,
    private val scheduleRepo: ScheduledNotificationRepository,
    private val userRepo: UserRepository,
    private val groupRepo: GroupRepository,
    private val scheduler: Scheduler,
    private val bot: NotificationBot
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val mapper = jacksonObjectMapper()

    /* ------------------------------------------------------------------ */
    /* ===== 1. Мгновенная рассылка (исп. WebhookController) ============ */
    /* ------------------------------------------------------------------ */

    /**
     * Метод нужен WebhookController’у — создаёт Event и сразу рассылает.
     * Внутри использует notifyImmediate, чтобы не дублировать логику.
     */
    @Transactional
    fun handleImmediateEvent(payload: Map<String, Any>) {
        val type = EventType.valueOf(payload["type"] as String)
        val groups = (payload["groupIds"] as? List<String>).orEmpty().map(UUID::fromString).toSet()
        val users  = (payload["userIds"]  as? List<String>).orEmpty().map(UUID::fromString).toSet()

        @Suppress("UNCHECKED_CAST")
        val data = payload["data"] as? Map<String, String> ?: emptyMap()

        notifyImmediate(type, data, groups, users)
    }

    /**
     * Сразу рассылает сообщение получателям groupIds + userIds по шаблону eventType.
     */
    @Transactional(readOnly = true)
    fun notifyImmediate(
        eventType: EventType,
        payload: Map<String, String>,
        groupIds: Set<UUID>,
        userIds: Set<UUID>
    ) {
        val template = templateRepo.findByEventType(eventType)
            ?: run {
                log.warn("Шаблон для $eventType не найден — уведомление пропущено")
                return
            }

        /* Сбор получателей */
        val directUsers = userRepo.findAllById(userIds).toSet()
        val groupUsers =
            if (groupIds.isEmpty()) emptySet()
            else groupRepo.findAllById(groupIds)
                .flatMap { grp -> userRepo.findAllByRolesContains(grp) }
                .toSet()

        val recipients = directUsers + groupUsers
        if (recipients.isEmpty()) {
            log.info("Нет получателей — рассылка пропущена")
            return
        }

        val text = render(template.text, payload)
        recipients.forEach { bot.sendText(it.chatId, text) }
        log.info("Отправлено ${recipients.size} сообщений (мгновенно)")
    }

    /* ------------------------------------------------------------------ */
    /* ===== 2. Создание отложенной рассылки (ScheduleController) ======= */
    /* ------------------------------------------------------------------ */

    @Transactional
    fun schedule(dto: ScheduleDto): ScheduledNotification {
        val entity = ScheduledNotification(
            template = templateRepo.getById(dto.templateId),
            eventTime = dto.eventTime,
            repeatCount = dto.repeatCount,
            repeatIntervalMinutes = dto.repeatIntervalMinutes,
            targetUsers = userRepo.findAllById(dto.targetUserIds).toSet(),
            targetGroups = groupRepo.findAllById(dto.targetGroupIds).toSet()
        ).let(scheduleRepo::save)

        val (job, trigger) = NotificationJobBuilder.build(entity)
        scheduler.scheduleJob(job, trigger)
        log.info("Создана Quartz-задача для расписания ${entity.id}")
        return entity
    }

    /* ------------------------------------------------------------------ */
    /* ===== 3. Вызывается Quartz-задачей — sendScheduled =============== */
    /* ------------------------------------------------------------------ */

    @Transactional
    fun sendScheduled(scheduleId: UUID) {
        val sn = scheduleRepo.findById(scheduleId).orElse(null)
            ?: return log.warn("Расписание $scheduleId не найдено")

        val recipients =
            sn.targetUsers +
            sn.targetGroups.flatMap { grp -> userRepo.findAllByRolesContains(grp) }

        if (recipients.isEmpty()) {
            log.info("[$scheduleId] нет получателей — пропущено")
            return
        }

        val text = render(
            sn.template.text,
            mapOf("time" to sn.eventTime.toString())
        )
        recipients.forEach { bot.sendText(it.chatId, text) }
        log.info("[$scheduleId] отправлено ${recipients.size} сообщений")
    }

    /* ------------------------------------------------------------------ */
    /* ================= helpers + дополнительные методы =============== */
    /* ------------------------------------------------------------------ */

    /** Для ScheduleController — вернуть все расписания */
    fun listAll(): List<ScheduledNotification> = scheduleRepo.findAll()

    /** Плейсхолдеры {{key}} → value */
    private fun render(tpl: String, payload: Map<String, String>): String =
        payload.entries.fold(tpl) { acc, (k, v) -> acc.replace("{{${k}}}", v) }
}
