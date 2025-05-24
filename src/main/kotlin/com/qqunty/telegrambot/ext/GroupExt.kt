// src/main/kotlin/com/qqunty/telegrambot/ext/GroupExt.kt
package com.qqunty.telegrambot.ext

import com.qqunty.telegrambot.domain.Group

/**
 * Возвращает список chatId всех участников группы в формате Long
 */
fun Group.chatIdList(): List<Long> =
    this.users.mapNotNull { it.chatId?.toLongOrNull() }
