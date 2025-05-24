package com.qqunty.telegrambot.util

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.domain.User
import com.qqunty.telegrambot.web.dto.UserDto
import java.util.UUID


fun User.toDto(): UserDto =
    UserDto(
        id      = this.id,
        chatId  = this.chatId ?: "",
        roleIds = this.roles.map { it.id }.toSet()
    )

fun UserDto.toEntity(roles: Collection<Group> = emptyList()): User =
    User(
        id     = this.id ?: UUID.randomUUID(),
        chatId = this.chatId,
        roles  = roles.toMutableSet()
    )
