package com.qqunty.telegrambot.web.dto

import java.util.UUID

data class ImmediateEventDto(
    val templateId: UUID,
    val channel: String,          // "GROUP" | "PRIVATE" | "BOTH"
    val groupId: UUID?,           // если channel = GROUP|BOTH
    val chatId: String?,          // если channel = PRIVATE|BOTH
    val payload: Map<String, Any>
)
