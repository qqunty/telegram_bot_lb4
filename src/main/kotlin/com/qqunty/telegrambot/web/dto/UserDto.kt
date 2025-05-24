package com.qqunty.telegrambot.web.dto

import java.util.UUID

data class UserDto(
  val id: UUID? = null,
  val chatId: String,
  val roleIds: Set<UUID> = emptySet()
)

