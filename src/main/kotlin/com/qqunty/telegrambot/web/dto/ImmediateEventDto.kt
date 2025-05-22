package com.qqunty.telegrambot.web.dto

import com.qqunty.telegrambot.domain.EventType
import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.domain.User

data class ImmediateEventDto(
    val type: EventType,
    val payload: Map<String, Any>,
    val targetGroups: List<Group> = emptyList(),
    val targetUsers: List<User> = emptyList()
)
