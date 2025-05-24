package com.qqunty.telegrambot.web.dto

import java.util.UUID

data class ImmediateEventDto(
    val templateId: UUID,
    val channel: String,          
    val groupId: UUID?,    
    val chatId: String?,        
    val payload: Map<String, Any>
)
