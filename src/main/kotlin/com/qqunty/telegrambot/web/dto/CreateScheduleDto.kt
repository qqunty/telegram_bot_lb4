package com.qqunty.telegrambot.web.dto

import java.time.Instant
import java.util.*

/** JSON‑запрос на создание отложенной рассылки */
data class CreateScheduleDto(
    val templateId: UUID,
    val eventTime: Instant,
    val repeatCount: Int = 0,
    val repeatIntervalMinutes: Int = 0,
    val targetGroupIds: List<UUID> = emptyList(),
    val targetUserIds:  List<UUID> = emptyList()
)
