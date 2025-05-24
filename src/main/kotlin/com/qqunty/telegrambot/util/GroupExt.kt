package com.qqunty.telegrambot.util

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.web.dto.GroupDto
import java.util.UUID

fun Group.toDto(): GroupDto =
    GroupDto(
        id          = this.id,
        name        = this.name,
        description = this.description,    
        users       = this.users.map { it.toDto() }
    )

fun GroupDto.toEntity(): Group =
    Group(
        id          = this.id ?: UUID.randomUUID(),
        name        = this.name,
        description = this.description       
    )
