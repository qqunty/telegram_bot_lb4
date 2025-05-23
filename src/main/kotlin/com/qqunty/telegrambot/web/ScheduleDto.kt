package com.qqunty.telegrambot.web

import java.time.Instant
import java.util.*

data class ScheduleDto(
    val templateId: UUID,
    val eventTime: Instant,
    val repeatCount: Int,
    val repeatIntervalMinutes: Int,
    val targetGroupIds: List<UUID>,
    val targetUserIds: List<UUID>
)
