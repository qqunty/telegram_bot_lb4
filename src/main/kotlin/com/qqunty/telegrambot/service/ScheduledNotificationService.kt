package com.qqunty.telegrambot.service

import com.qqunty.telegrambot.domain.ScheduledNotification
import com.qqunty.telegrambot.repository.ScheduledNotificationRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class ScheduledNotificationService(
    private val repository: ScheduledNotificationRepository
) {
    fun findAll(): List<ScheduledNotification> = repository.findAll()

    fun create(
        templateId: UUID,
        eventTime: Instant,
        repeatCount: Int,
        repeatIntervalMinutes: Int,
        targetGroupIds: List<UUID>,
        targetUserIds: List<UUID>
    ): ScheduledNotification {
        val scheduledNotification = ScheduledNotification(
            id = UUID.randomUUID(),
            templateId = templateId,
            eventTime = eventTime,
            repeatCount = repeatCount,
            repeatIntervalMinutes = repeatIntervalMinutes,
            targetChatIds = emptyList()
        )
        return repository.save(scheduledNotification)
    }
}
