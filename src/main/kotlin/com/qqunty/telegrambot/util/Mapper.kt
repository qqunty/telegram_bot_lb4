package com.qqunty.telegrambot.util

import com.qqunty.telegrambot.domain.User
import org.telegram.telegrambots.meta.api.objects.User as TgUser

fun TgUser.toDomain(): User =
    User(chatId = this.id.toString())
