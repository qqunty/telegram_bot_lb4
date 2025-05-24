// src/main/kotlin/com/qqunty/telegrambot/web/dto/GroupDto.kt
package com.qqunty.telegrambot.web.dto

import java.util.UUID

data class GroupDto(
    val id: UUID?            = null,
    val name: String,
    val description: String?,         
    val users: List<UserDto> = emptyList()
)
