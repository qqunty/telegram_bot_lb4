package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "templates")
data class Template(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val eventType: EventType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val channel: Channel,

    @Lob
    @Column(nullable = false)
    val text: String
)
