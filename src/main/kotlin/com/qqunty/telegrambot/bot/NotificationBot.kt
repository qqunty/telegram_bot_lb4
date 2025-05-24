package com.qqunty.telegrambot.bot

import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession


@Component
class NotificationBot(
    @Value("\${telegram.bot.token}")    private val token: String,
    @Value("\${telegram.bot.username}") private val username: String
) : TelegramLongPollingBot() {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun getBotToken(): String = token
    override fun getBotUsername(): String = username

    override fun onUpdateReceived(update: Update) {
        // обрабатываем только команду /start
        val text = update.message?.text ?: return
        if (text.startsWith("/start")) {
            val chatId = update.message.chatId.toString()
            execute(SendMessage(chatId, "Привет! Я готов рассылать уведомления."))
            log.info("Ответили /start в чат $chatId")
        }
    }

    fun sendText(chatId: String, text: String) {
        try {
            execute(SendMessage(chatId, text))
        } catch (ex: TelegramApiException) {
            log.error("Failed to send message to $chatId", ex)
        }
    }
}
