// src/main/kotlin/com/qqunty/telegrambot/web/dto/UserDto.kt
package com.qqunty.telegrambot.web.dto

import java.util.*

data class UserDto(
    val id: UUID?,
    val chatId: String,
    val firstName: String?,
    val lastName: String?
)
