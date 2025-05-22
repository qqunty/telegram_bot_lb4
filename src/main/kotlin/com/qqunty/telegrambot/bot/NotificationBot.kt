package com.qqunty.telegrambot.bot

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Component
class NotificationBot(
    @Value("\${telegram.bot.token}") private val token: String,
    @Value("\${telegram.bot.username}") private val username: String,
) : TelegramLongPollingBot(token) {

    private val log = LoggerFactory.getLogger(javaClass)

    /** <-- 1. регистрируемся при старте Spring‑контекста */
    @PostConstruct
    fun init() {
        val api = TelegramBotsApi(DefaultBotSession::class.java)
        api.registerBot(this)
        log.info("Telegram‑бот «{}» успешно зарегистрирован", username)
    }

    override fun getBotUsername() = username
    override fun getBotToken() = token

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.text?.startsWith("/start") == true) {
            val chatId = update.message.chatId.toString()
            execute(SendMessage(chatId, "Бот запущен! 🎉"))
            log.info("Ответили /start в чат {}", chatId)
        }
    }
}
