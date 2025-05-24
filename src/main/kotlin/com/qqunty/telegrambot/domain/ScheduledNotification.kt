package com.qqunty.telegrambot.domain

import jakarta.persistence.FetchType
import java.time.LocalDateTime
import jakarta.persistence.*
import java.time.Instant
import java.util.*
import com.qqunty.telegrambot.web.dto.ScheduleDto

@Entity
@Table(name = "scheduled_notifications")
class ScheduledNotification(
    @Id val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY) val template: Template,
    val eventTime: LocalDateTime,
    val repeatIntervalMinutes: Int,
    val repeatCount: Int,

    /** Кому отправлять */
    @ElementCollection val targetChatIds: Set<String> = emptySet()
) {

    companion object {
        fun fromDto(dto: ScheduleDto, template: Template): ScheduledNotification =
            ScheduledNotification(
                template = template,
                eventTime = dto.eventTime,
                repeatIntervalMinutes = dto.repeatIntervalMinutes,
                repeatCount = dto.repeatCount,
                targetChatIds = dto.targetUserChatIds
            )
    }
}
