package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "scheduled_notifications")
data class ScheduledNotification(
    @Id
    val id: UUID = UUID.randomUUID(),

    val templateId: UUID,

    val eventTime: Instant,

    val repeatCount: Int,

    val repeatIntervalMinutes: Int,

    @ElementCollection
    val targetChatIds: List<String> = emptyList()
)
