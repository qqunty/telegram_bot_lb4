package com.qqunty.telegrambot.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.qqunty.telegrambot.bot.NotificationBot
import com.qqunty.telegrambot.domain.*
import com.qqunty.telegrambot.dto.ImmediateEventDto
import com.qqunty.telegrambot.repository.*
import com.qqunty.telegrambot.util.sendText
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class NotificationService(
    private val eventRepo: EventRepository,
    private val templateRepo: TemplateRepository,
    private val scheduleRepo: ScheduledNotificationRepository,
    private val groupRepo: GroupRepository,
    private val userRepo: UserRepository,
    private val mapper: ObjectMapper,
    private val bot: NotificationBot
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /** перенос расписаний из БД в Quartz (см. предыдущие шаги) */
    init {
        val migrated = scheduleRepo.findAll().onEach { schedule(it) }.size
        log.info("Перенесли {} отложенных рассылок из БД в планировщик", migrated)
    }

    /** планирование одного объекта ScheduledNotification  */
    fun schedule(sn: ScheduledNotification) { /* … как раньше … */ }

    // --- НОВЫЙ МЕТОД --------------------------------------------------------

    /** мгновенное уведомление, пришедшее через /api/webhook */
    @Transactional
    fun handleImmediateEvent(dto: ImmediateEventDto) {
        // 1️⃣  пишем Event в базу
        val event = eventRepo.save(
            Event(
                type = dto.eventType,
                payload = mapper.writeValueAsString(dto.payload),
                createdAt = Instant.now()
            )
        )

        // 2️⃣  ищем шаблон
        val tpl = templateRepo.findFirstByEventType(dto.eventType)
        if (tpl == null) {
            log.warn("Нет шаблона для {}", dto.eventType); return
        }

        // 3️⃣  подставляем плейсхолдеры
        val ready = tpl.text
            .replace("{{link}}",  dto.payload["link"]?.toString()  ?: "")
            .replace("{{place}}", dto.payload["place"]?.toString() ?: "")
            .replace("{{time}}",  dto.payload["time"]?.toString()  ?: "")

        // 4️⃣  рассылаем согласно каналу
        when (tpl.channel) {
            Channel.PRIVATE -> dto.targetChatIds.forEach       { bot.sendText(it, ready) }
            Channel.GROUP   -> dto.targetGroupChatIds.forEach  { bot.sendText(it, ready) }
            Channel.BOTH    -> {
                dto.targetChatIds.forEach      { bot.sendText(it, ready) }
                dto.targetGroupChatIds.forEach { bot.sendText(it, ready) }
            }
        }

        log.info("Instant notification sent for Event {}", event.id)
    }
}
