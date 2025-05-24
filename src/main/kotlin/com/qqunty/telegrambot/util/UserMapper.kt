// src/main/kotlin/com/qqunty/telegrambot/util/UserMapper.kt
package com.qqunty.telegrambot.util

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.domain.User
import com.qqunty.telegrambot.web.dto.UserDto
import java.util.UUID

/**
 * Преобразует JPA-Entity User в DTO
 */
fun User.toDto(): UserDto =
    UserDto(
        id      = this.id,
        chatId  = this.chatId ?: "",
        roleIds = this.roles.map { it.id }.toSet()
    )

/**
 * Преобразует DTO в JPA-Entity User.
 * @param roles — при создании/обновлении DTO вы можете передать туда уже загруженные Group-сущности
 */
fun UserDto.toEntity(roles: Collection<Group> = emptyList()): User =
    User(
        id     = this.id ?: UUID.randomUUID(),
        chatId = this.chatId,
        roles  = roles.toMutableSet()
    )
