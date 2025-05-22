package com.qqunty.telegrambot.service

import com.qqunty.telegrambot.bot.NotificationBot
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class NotificationService(
    @Lazy private val bot: NotificationBot
) {
    /**
     * Отправляет простое текстовое сообщение.
     */
    fun sendText(chatId: String, text: String) {
        val msg = SendMessage(chatId, text)
        bot.execute(msg)
    }
}
