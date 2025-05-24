package com.qqunty.telegrambot.web.dto

import com.qqunty.telegrambot.domain.EventType
import com.qqunty.telegrambot.domain.TemplateChannel
import java.util.*

data class TemplateDto(
    val id: UUID?,
    val eventType: EventType,
    val channel: TemplateChannel,
    val text: String
)
