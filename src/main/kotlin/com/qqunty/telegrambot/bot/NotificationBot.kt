package com.qqunty.telegrambot.bot

import com.qqunty.telegrambot.service.NotificationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class NotificationBot(
    @Value("\${telegram.bot.token}") private val token: String,
    @Value("\${telegram.bot.username}") private val username: String,
    @Lazy private val notificationService: NotificationService
) : TelegramLongPollingBot() {

    private val log = LoggerFactory.getLogger(NotificationBot::class.java)

    override fun getBotUsername(): String = username
    override fun getBotToken(): String = token

    override fun onUpdateReceived(update: Update) {
        log.info("Получено обновление: {}", update)
        if (update.hasMessage() && update.message.text == "/start") {
            val chatId = update.message.chatId.toString()
            log.info("Делегируем отправку сервису в чат {}", chatId)
            notificationService.sendText(chatId, "Бот запущен! 🎉")
        }
    }
}
