package com.qqunty.telegrambot.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.qqunty.telegrambot.domain.Channel
import com.qqunty.telegrambot.domain.ScheduledNotification
import com.qqunty.telegrambot.ext.chatIdList
import com.qqunty.telegrambot.repository.*
import com.qqunty.telegrambot.web.dto.ImmediateEventDto
import com.qqunty.telegrambot.web.dto.ScheduleDto
import org.quartz.Scheduler
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.util.UUID

@Service
class NotificationService(
    private val templateRepo: TemplateRepository,
    private val snRepo: ScheduledNotificationRepository,
    private val userRepo: UserRepository,
    private val groupRepo: GroupRepository,
    private val scheduler: Scheduler,
    private val jobBuilder: NotificationJobBuilder,
    private val messageSender: MessageSender
) {

    private val mapper = jacksonObjectMapper()
    
    @Transactional(readOnly = true)
    fun listAll(): List<ScheduledNotification> = snRepo.findAll()

    @Transactional
    fun notifyImmediate(dto: ImmediateEventDto) {
        val template = templateRepo.findById(dto.templateId).orElseThrow()
        val text = template.text.replacePlaceholders(dto.payload)

        val chatIds: List<Long> = when (template.channel) {
            Channel.GROUP   ->
                groupRepo.findById(dto.groupId!!).get().chatIdList()

            Channel.PRIVATE ->
                listOf(dto.chatId!!.toLong())

            Channel.BOTH    ->
                groupRepo.findById(dto.groupId!!).get().chatIdList() +
                listOf(dto.chatId!!.toLong())
        }

        chatIds.forEach { messageSender.send(it, text) }
    }

    @Transactional
    fun schedule(dto: ScheduleDto): ScheduledNotification {
        val template = templateRepo.findById(dto.templateId).orElseThrow()
        val sn = ScheduledNotification.fromDto(dto, template)
        snRepo.save(sn)    

        val (job, trigger) = jobBuilder.buildJob(
            text          = template.text,
            chatIds       = sn.targetChatIds.mapNotNull { it.toLongOrNull() },
            fireAt        = sn.eventTime.atZone(ZoneId.systemDefault()).toInstant(),
            repeatMinutes = sn.repeatIntervalMinutes
        )

        scheduler.scheduleJob(job, trigger)
        return sn
    }

    private fun String.replacePlaceholders(payload: Map<String, Any>): String =
        payload.entries.fold(this) { acc, (k, v) -> acc.replace("{{${k}}}", v.toString()) }
}
