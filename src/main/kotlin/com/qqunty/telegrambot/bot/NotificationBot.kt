// src/main/kotlin/com/qqunty/telegrambot/bot/NotificationBot.kt
package com.qqunty.telegrambot.bot

import com.qqunty.telegrambot.domain.User
import com.qqunty.telegrambot.repository.GroupRepository
import com.qqunty.telegrambot.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import com.qqunty.telegrambot.domain.Group
@Component
class NotificationBot(
    private val groupRepo: GroupRepository,
    private val userRepo: UserRepository,
    @Value("\${telegram.bot.username}") private val botUsername: String,
    @Value("\${telegram.bot.token}")    private val botToken: String
) : TelegramLongPollingBot() {

    override fun getBotUsername(): String = botUsername
    override fun getBotToken(): String = botToken

    @PostConstruct
    fun registerBot() {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(this)
    }

    override fun onUpdateReceived(update: Update) {
        if (!update.hasMessage() || !update.message.hasText()) return

        val chatId = update.message.chatId.toString()
        val text   = update.message.text.trim()
        val parts  = text.split("\\s+".toRegex())

        when (parts[0].lowercase()) {
            "/start" -> send(chatId, "Привет! Я готов рассылать уведомления.")
            "/list-groups" -> {
                val names = groupRepo.findAll().map { it.name }
                val reply = if (names.isEmpty()) "Нет доступных групп." else "Группы: ${names.joinToString(", ")}"
                send(chatId, reply)
            }
            "/subscribe" -> {
                if (parts.size < 2) {
                    send(chatId, "Использование: /subscribe <имя_группы>")
                } else {
                    val groupName = parts[1]
                    val group = groupRepo.findByName(groupName)
                    if (group == null) {
                        send(chatId, "Группа '$groupName' не найдена.")
                    } else {
                        // найдём или создадим пользователя по chatId
                        val user = userRepo.findByChatId(chatId)
                            ?: User(chatId = chatId).also { userRepo.save(it) }

                        user.roles.add(group)
                        userRepo.save(user)
                        send(chatId, "Вы подписаны на группу '$groupName'.")
                    }
                }
            }
            "/create-group" -> {
                if (parts.size < 3) {
                    send(chatId, "Использование: /create-group <name> <description>")
                } else {
                    val (name, desc) = parts[1] to parts.drop(2).joinToString(" ")
                    groupRepo.save(Group(name = name, description = desc))
                    send(chatId, "Группа '$name' создана.")
                }
            }

            else -> {
            }
        }
    }

    fun send(chatId: String, text: String) {
        execute(SendMessage(chatId, text))
    }
}
