// src/main/kotlin/com/qqunty/telegrambot/util/Mapper.kt
package com.qqunty.telegrambot.util

import com.qqunty.telegrambot.domain.User
import com.qqunty.telegrambot.web.dto.UserDto

fun User.toDto() = UserDto(id, chatId, firstName, lastName)
fun UserDto.toEntity() = User(id, chatId, firstName, lastName)
