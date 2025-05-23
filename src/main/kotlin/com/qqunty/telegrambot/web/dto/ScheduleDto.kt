package com.qqunty.telegrambot.web.dto

import java.time.Instant
import java.util.*

data class ScheduleDto(
  val templateId: UUID,
  val eventTime: Instant,
  val repeatCount: Int,
  val repeatIntervalMinutes: Int,
  val targetUserIds: Set<UUID> = emptySet(),
  val targetGroupIds: Set<UUID> = emptySet()
)

