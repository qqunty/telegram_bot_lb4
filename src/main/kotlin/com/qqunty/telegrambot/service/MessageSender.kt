package com.qqunty.telegrambot.service

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class MessageSender(private val bot: AbsSender) {

    fun send(chatId: Long, text: String) {
        val msg = SendMessage(chatId.toString(), text)
        bot.execute(msg)
    }
}
