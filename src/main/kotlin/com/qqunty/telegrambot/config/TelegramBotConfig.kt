package com.qqunty.telegrambot.config

import com.qqunty.telegrambot.bot.NotificationBot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class TelegramBotConfig {

    @Bean
    fun telegramBotsApi(bot: NotificationBot): TelegramBotsApi =
        TelegramBotsApi(DefaultBotSession::class.java).apply {
            registerBot(bot)          // <— ключевая строка
        }
}
