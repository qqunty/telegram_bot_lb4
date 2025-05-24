// src/main/kotlin/com/qqunty/telegrambot/domain/ScheduledNotification.kt
package com.qqunty.telegrambot.domain

import com.qqunty.telegrambot.web.dto.ScheduleDto
import jakarta.persistence.*
import java.time.Instant
import java.time.ZoneId
import java.util.*

@Entity
@Table(name = "scheduled_notifications")
class ScheduledNotification(

    @Id
    val id: UUID = UUID.randomUUID(),

    /** Время события */
    val eventTime: Instant? = null,

    /** Сколько раз повторять */
    val repeatCount: Int = 0,

    /** Интервал повтора в минутах */
    val repeatIntervalMinutes: Int = 0,

    @ManyToOne
    @JoinColumn(name = "template_id")
    val template: Template? = null,

    /** Список Telegram-chatId’ов, куда шлём напоминание */
    @ElementCollection
    @CollectionTable(
        name = "scheduled_notification_target_chat_ids",
        joinColumns = [JoinColumn(name = "scheduled_notification_id")]
    )
    @Column(name = "target_chat_ids")
    val targetChatIds: MutableList<String> = mutableListOf()

) {
    // Для Hibernate
    constructor() : this(UUID.randomUUID(), null, 0, 0, null, mutableListOf())

    companion object {
        fun fromDto(dto: ScheduleDto, template: Template): ScheduledNotification {
            return ScheduledNotification(
                id = UUID.randomUUID(),
                // конвертация из LocalDateTime в Instant по системной зоне
                eventTime = dto.eventTime.atZone(ZoneId.systemDefault()).toInstant(),
                repeatCount = dto.repeatCount,
                repeatIntervalMinutes = dto.repeatIntervalMinutes,
                template = template,
                // просто копируем те chatId, что передал клиент
                targetChatIds = dto.targetUserChatIds.toMutableList()
            )
        }
    }
}
