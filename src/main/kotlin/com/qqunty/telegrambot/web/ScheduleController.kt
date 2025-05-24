package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.ScheduledNotification
import com.qqunty.telegrambot.service.ScheduledNotificationService
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

data class CreateScheduleRequest(
    val templateId: UUID,
    val eventTime: Long,
    val repeatCount: Int,
    val repeatIntervalMinutes: Int,
    val targetGroupIds: List<UUID> = emptyList(),
    val targetUserIds: List<UUID> = emptyList()
)

@RestController
@RequestMapping("/api/schedules")
class ScheduleController(
    private val scheduleService: ScheduledNotificationService
) {
    @GetMapping
    fun listAll(): List<ScheduledNotification> = scheduleService.findAll()

    @PostMapping
    fun create(@RequestBody req: CreateScheduleRequest): ScheduledNotification =
        scheduleService.create(
            templateId = req.templateId,
            eventTime = Instant.ofEpochMilli(req.eventTime),
            repeatCount = req.repeatCount,
            repeatIntervalMinutes = req.repeatIntervalMinutes,
            targetGroupIds = req.targetGroupIds,
            targetUserIds = req.targetUserIds
        )
}
