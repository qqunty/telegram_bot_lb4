package com.qqunty.telegrambot.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.qqunty.telegrambot.bot.NotificationBot          // ①
import com.qqunty.telegrambot.domain.*
import com.qqunty.telegrambot.repository.*
import com.qqunty.telegrambot.web.dto.ImmediateEventDto     // ②
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import com.qqunty.telegrambot.bot.sendText


@Service
class NotificationService(
    private val templateRepo: TemplateRepository,
    private val groupRepo: GroupRepository,
    private val userRepo: UserRepository,
    private val schedRepo: ScheduledNotificationRepository,
    private val bot: NotificationBot,                       // ← чтобы вызывать execute()
    private val mapper: ObjectMapper                        // ← инжектируем готовый бин
) {
    private val log = LoggerFactory.getLogger(javaClass)

    /** мгновенное событие из webhook или команды  */
    fun handleImmediateEvent(dto: ImmediateEventDto) {
        val eventJson = mapper.writeValueAsString(dto.payload)
        val event = Event(type = dto.type, payload = eventJson)
        // TODO: eventRepo.save(event) если нужен лог в БД

        // ищем подходящие шаблоны под тип события
        val templates = templateRepo.findAll()
            .filter { it.eventType == dto.type }

        templates.forEach { tpl ->
            when (tpl.channel) {
                Channel.PRIVATE, Channel.BOTH ->
                    dto.targetUsers.forEach { u -> bot.sendText(u.chatId, tpl.text.fill(dto.payload)) }
                Channel.GROUP, Channel.BOTH   ->
                    dto.targetGroups.forEach { g -> bot.sendText(g.name, tpl.text.fill(dto.payload)) }
            }
        }
    }

    /** утилита – подставляем плейсхолдеры {{link}}, {{place}} … */
    private fun String.fill(map: Map<String, Any>) =
        map.entries.fold(this) { acc, (k, v) -> acc.replace("{{$k}}", v.toString()) }
}
