package com.qqunty.telegrambot.dto

import com.qqunty.telegrambot.domain.EventType

data class ImmediateEventDto(
    val eventType: EventType,
    val payload: Map<String, Any>,
    val targetChatIds: List<String> = emptyList(),
    val targetGroupChatIds: List<String> = emptyList()
)
