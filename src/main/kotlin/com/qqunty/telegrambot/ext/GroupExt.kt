package com.qqunty.telegrambot.ext

import com.qqunty.telegrambot.domain.Group

/** chatId всех участников группы, переведённые в Long */
fun Group.chatIdList(): List<Long> =
    users.mapNotNull { it.chatId?.toLongOrNull() }
