package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "templates")
class Template(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    val eventType: EventType,

    @Enumerated(EnumType.STRING)
    val channel: Channel,

    @Lob
    val text: String
) {
    constructor() : this(UUID.randomUUID(), EventType.CALL, Channel.GROUP, "")
}
