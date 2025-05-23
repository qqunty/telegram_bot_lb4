package com.qqunty.telegrambot.util

import com.qqunty.telegrambot.bot.NotificationBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

fun NotificationBot.sendText(chatId: String, text: String) {
    execute(
        SendMessage.builder()
            .chatId(chatId)
            .text(text)
            .build()
    )
}
