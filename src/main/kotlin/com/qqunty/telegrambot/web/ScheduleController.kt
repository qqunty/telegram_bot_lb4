package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.ScheduledNotification
import com.qqunty.telegrambot.repository.GroupRepository
import com.qqunty.telegrambot.repository.TemplateRepository
import com.qqunty.telegrambot.repository.UserRepository
import com.qqunty.telegrambot.service.NotificationService
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/api/schedules")
class ScheduleController(
    private val templateRepo: TemplateRepository,
    private val groupRepo: GroupRepository,
    private val userRepo: UserRepository,
    private val service: NotificationService
) {
    @PostMapping
    fun create(@RequestBody dto: CreateScheduleDto): ScheduledNotification {
        val sn = ScheduledNotification(
            template = templateRepo.getReferenceById(dto.templateId),
            eventTime = dto.eventTime,
            repeatCount = dto.repeatCount,
            repeatIntervalMinutes = dto.repeatIntervalMinutes,
            targetGroups = groupRepo.findAllById(dto.targetGroupIds).toSet(),
            targetUsers = userRepo.findAllById(dto.targetUserIds).toSet()
        )
        return service.schedule(sn)   // кладём и в БД, и в планировщик
    }
}

data class CreateScheduleDto(
    val templateId: UUID,
    val eventTime: Instant,
    val repeatCount: Int,
    val repeatIntervalMinutes: Int,
    val targetGroupIds: List<UUID>,
    val targetUserIds: List<UUID>
)
