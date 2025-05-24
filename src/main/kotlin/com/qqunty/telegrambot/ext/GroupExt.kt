package com.qqunty.telegrambot.ext

import com.qqunty.telegrambot.domain.Group

/** chatId всех участников группы в формате Long */
fun Group.chatIdList(): List<Long> =
    this.users.mapNotNull { it.chatId?.toLongOrNull() }
