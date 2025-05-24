// src/main/kotlin/com/qqunty/telegrambot/util/Mapper.kt
package com.qqunty.telegrambot.util

import com.qqunty.telegrambot.domain.User
import org.telegram.telegrambots.meta.api.objects.User as TgUser

/**
 * Преобразование пользователя Telegram в доменную сущность User.
 * • chatId = id Telegram-пользователя
 * • id генерируется самим классом User
 * • roles остаётся пустым (заполняется позже при подписке на группы)
 */
fun TgUser.toDomain(): User =
    User(chatId = this.id.toString())
