package com.qqunty.telegrambot.web.dto

import com.qqunty.telegrambot.domain.Channel
import com.qqunty.telegrambot.domain.EventType
import java.util.*

data class TemplateDto(
    val eventType: EventType,
    val channel: Channel,
    val text: String
)
