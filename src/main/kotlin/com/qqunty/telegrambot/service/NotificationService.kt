package com.qqunty.telegrambot.service

import com.qqunty.telegrambot.model.*
import com.qqunty.telegrambot.repository.*
import com.qqunty.telegrambot.job.NotificationJobBuilder
import org.quartz.Scheduler
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val templateRepo: TemplateRepository,
    private val scheduleRepo: ScheduledNotificationRepository,
    private val userRepo: UserRepository,
    private val groupRepo: GroupRepository,
    private val scheduler: Scheduler
) {

    fun schedule(dto: ScheduleDto): ScheduledNotification {
        val saved = scheduleRepo.save(
            ScheduledNotification(
                template = templateRepo.getById(dto.templateId),
                eventTime = dto.eventTime,
                repeatCount = dto.repeatCount,
                repeatIntervalMinutes = dto.repeatIntervalMinutes,
                targetUsers = userRepo.findAllById(dto.targetUserIds).toSet(),
                targetGroups = groupRepo.findAllById(dto.targetGroupIds).toSet()
            )
        )

        val (jobDetail, trigger) = NotificationJobBuilder.build(saved)
        scheduler.scheduleJob(jobDetail, trigger)
        return saved
    }

    fun notifyImmediate(type: EventType, payload: Map<String, String>, targets: Targets) {
        val template = templateRepo.findByEventType(type) ?: return
        // TODO: сформировать текст по шаблону и отправить сообщения
    }
}
