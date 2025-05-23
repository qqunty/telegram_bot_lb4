package com.qqunty.telegrambot.bot

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import java.io.Serializable

fun NotificationBot.sendText(chatId: Serializable, text: String) =
    execute(SendMessage(chatId.toString(), text))
