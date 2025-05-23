package com.qqunty.telegrambot.web.dto

import java.time.Instant
import java.util.*

data class CreateScheduleDto(
    val templateId: UUID,
    val eventTime: Instant,
    val repeatCount: Int = 0,
    val repeatIntervalMinutes: Int = 0,
    val targetGroupIds: List<UUID> = emptyList(),
    val targetUserIds:  List<UUID> = emptyList()
)
