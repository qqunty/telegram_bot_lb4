package com.qqunty.telegrambot.ext

import com.qqunty.telegrambot.domain.Group

fun Group.chatIdList(): List<Long> =
    this.users.mapNotNull { it.chatId?.toLongOrNull() }
