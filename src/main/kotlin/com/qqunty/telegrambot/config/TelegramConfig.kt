package com.qqunty.telegrambot.config

import com.qqunty.telegrambot.bot.NotificationBot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class TelegramConfig(
    private val notificationBot: NotificationBot
) {
    @Bean
    fun telegramBotsApi(): TelegramBotsApi {
        val api = TelegramBotsApi(DefaultBotSession::class.java)
        api.registerBot(notificationBot)
        return api
    }
}
