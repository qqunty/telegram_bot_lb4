// src/main/kotlin/com/qqunty/telegrambot/util/GroupExt.kt
package com.qqunty.telegrambot.util

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.web.dto.GroupDto
import java.util.UUID

// Entity → DTO
fun Group.toDto(): GroupDto =
    GroupDto(
        id          = this.id,
        name        = this.name,
        description = this.description,      // String? → String? теперь нормально
        users       = this.users.map { it.toDto() }
    )

// DTO → Entity
fun GroupDto.toEntity(): Group =
    Group(
        id          = this.id ?: UUID.randomUUID(),
        name        = this.name,
        description = this.description       // Entity тоже ожидает String? (если ваша сущность nullable)
    )
