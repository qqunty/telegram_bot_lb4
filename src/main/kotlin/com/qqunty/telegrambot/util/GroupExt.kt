// src/main/kotlin/com/qqunty/telegrambot/util/GroupMapper.kt
package com.qqunty.telegrambot.util

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.web.dto.GroupDto
import java.util.UUID

/**
 * Преобразует JPA-Entity Group в DTO
 */
fun Group.toDto(): GroupDto =
    GroupDto(
        id          = this.id,
        name        = this.name,
        description = this.description,
        users       = this.users.map { it.toDto() }  // User.toDto() из UserMapper
    )

/**
 * Преобразует DTO в JPA-Entity Group.
 * Пользователи (users) потом прикрепляются через сервисы / контроллеры.
 */
fun GroupDto.toEntity(): Group =
    Group(
        id          = this.id ?: UUID.randomUUID(),
        name        = this.name,
        description = this.description
    )

    