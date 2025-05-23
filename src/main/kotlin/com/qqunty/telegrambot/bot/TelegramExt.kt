package com.qqunty.telegrambot.bot

import org.slf4j.LoggerFactory
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

fun NotificationBot.sendText(chatId: String, text: String) {
    val log = LoggerFactory.getLogger("TelegramExt")

    try {
        execute(
            SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build()
        )
    } catch (e: TelegramApiException) {
        log.error("Не удалось отправить сообщение в чат $chatId", e)
    }
}
