package com.qqunty.telegrambot.service

import com.qqunty.telegrambot.bot.NotificationBot
import com.qqunty.telegrambot.domain.Template
import com.qqunty.telegrambot.domain.TemplateChannel
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val bot: NotificationBot
) {

    fun sendImmediate(template: Template, chatIds: List<String>, data: Map<String, String>) {
        var text = template.text
        text = text.replace("{{link}}", data["link"] ?: "")
                   .replace("{{place}}", data["place"] ?: "")
                   .replace("{{time}}", data["time"] ?: "")

        chatIds.forEach { bot.send(it, text) }
    }
}
