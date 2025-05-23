package com.qqunty.telegrambot.service

/**
 * Простейший интерфейс-обёртка для отправки сообщений.
 * Внедрите сюда ваш Telegram-бот (или иной транспорт) и реализуйте метод send.
 */
interface MessageSender {
    fun send(chatId: String, text: String)
}
