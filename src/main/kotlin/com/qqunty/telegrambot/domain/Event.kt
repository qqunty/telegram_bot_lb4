package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "events")
data class Event(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: EventType,

    @Lob
    @Column(nullable = false)
    val payload: String,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
)
