package com.qqunty.telegrambot.web.dto

import java.time.LocalDateTime
import java.util.UUID

data class ScheduleDto(
    val templateId: UUID,
    val eventTime: LocalDateTime,
    val repeatIntervalMinutes: Int,
    val repeatCount: Int,
    val targetGroupIds: Set<UUID>,
    val targetUserChatIds: Set<String>
)
