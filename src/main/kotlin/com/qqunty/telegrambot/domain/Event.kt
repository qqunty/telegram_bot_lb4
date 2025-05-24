// src/main/kotlin/com/qqunty/telegrambot/domain/Event.kt
package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "events")
class Event(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    val type: EventType,

    @Lob
    val payload: String,

    val createdAt: Instant = Instant.now()
) {
    constructor() : this(UUID.randomUUID(), EventType.CALL, "{}", Instant.now())
}
