package com.qqunty.telegrambot.bot

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.domain.User
import com.qqunty.telegrambot.repository.GroupRepository
import com.qqunty.telegrambot.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

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
                val reply = if (names.isEmpty()) "Нет доступных групп."
                            else "Группы: ${names.joinToString(", ")}"
                send(chatId, reply)
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

            "/subscribe" -> {
                if (parts.size < 2) {
                    send(chatId, "Использование: /subscribe <имя_группы>")
                    return
                }
                val groupName = parts[1]
                val group = groupRepo.findByName(groupName)
                if (group == null) {
                    send(chatId, "Группа '$groupName' не найдена.")
                    return
                }
                // найдём или создадим пользователя
                val user = userRepo.findByChatId(chatId) ?: User(chatId = chatId).also {
                    userRepo.save(it)
                }
                if (user.roles.any { it.name == groupName }) {
                    send(chatId, "Вы уже подписаны на '$groupName'.")
                } else {
                    user.roles.add(group)
                    userRepo.save(user)
                    send(chatId, "Вы подписаны на группу '$groupName'.")
                }
            }

            "/unsubscribe" -> {
                if (parts.size < 2) {
                    send(chatId, "Использование: /unsubscribe <имя_группы>")
                    return
                }
                val groupName = parts[1]
                val group = groupRepo.findByName(groupName)
                if (group == null) {
                    send(chatId, "Группа '$groupName' не найдена.")
                    return
                }
                val user = userRepo.findByChatId(chatId)
                if (user == null || user.roles.none { it.name == groupName }) {
                    send(chatId, "Вы не были подписаны на '$groupName'.")
                } else {
                    user.roles.removeIf { it.name == groupName }
                    userRepo.save(user)
                    send(chatId, "Вы отписаны от группы '$groupName'.")
                }
                return
            }
            
            else -> {
            }
        }
    }

    fun send(chatId: String, text: String) {
        execute(SendMessage(chatId, text))
    }
}
