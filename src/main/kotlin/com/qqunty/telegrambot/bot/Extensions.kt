package com.qqunty.telegrambot.bot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

/**
 * Удобная обёртка: bot.sendText(chatId, "текст")
 */
fun TelegramLongPollingBot.sendText(chatId: String, text: String) {
    val msg = SendMessage(chatId, text)
    // можно настроить parseMode и т.д. при желании
    execute(msg)
}
