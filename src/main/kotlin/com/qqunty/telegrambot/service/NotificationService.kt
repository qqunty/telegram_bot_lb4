package com.qqunty.telegrambot.service

import com.qqunty.telegrambot.bot.NotificationBot
import com.qqunty.telegrambot.domain.Template
import com.qqunty.telegrambot.domain.TemplateChannel
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val bot: NotificationBot
) {

    /**
     * Мгновенная рассылка:
     * @param template – шаблон
     * @param chatIds – список chatId (строки)
     * @param data – мапа плейсхолдеров: "link", "place", "time"
     */
    fun sendImmediate(template: Template, chatIds: List<String>, data: Map<String, String>) {
        var text = template.text
        text = text.replace("{{link}}", data["link"] ?: "")
                   .replace("{{place}}", data["place"] ?: "")
                   .replace("{{time}}", data["time"] ?: "")

        // отсылаем всем
        chatIds.forEach { bot.send(it, text) }
    }
}
