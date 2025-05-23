package com.qqunty.telegrambot.ext

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import com.qqunty.telegrambot.bot.NotificationBot

fun NotificationBot.sendText(chatId: String, text: String) =
    execute( SendMessage(chatId, text) )
