package com.qqunty.telegrambot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TelegramBotLb4Application

fun main(args: Array<String>) {
    runApplication<TelegramBotLb4Application>(*args)
}
