package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.ScheduledNotification
import com.qqunty.telegrambot.repository.*
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/api/web/schedules")
class ScheduleController(
    private val tplRepo: TemplateRepository,
    private val grpRepo: GroupRepository,
    private val usrRepo: UserRepository,
    private val schedRepo: ScheduledNotificationRepository
) {
    @GetMapping fun list() = schedRepo.findAll()

    @PostMapping fun create(@RequestBody dto: CreateScheduleDto): ScheduledNotification {
        val template = tplRepo.findById(dto.templateId).orElseThrow()
        val groups   = grpRepo.findAllById(dto.targetGroupIds).toSet()
        val users    = usrRepo.findAllById(dto.targetUserIds).toSet()
        val s = ScheduledNotification(
            template = template,
            eventTime = dto.eventTime,
            repeatCount = dto.repeatCount,
            repeatIntervalMinutes = dto.repeatIntervalMinutes,
            targetGroups = groups,
            targetUsers = users
        )
        return schedRepo.save(s)
    }
}

data class CreateScheduleDto(
    val templateId: UUID,
    val eventTime: Instant,
    val repeatCount: Int = 0,
    val repeatIntervalMinutes: Int = 0,
    val targetGroupIds: List<UUID> = emptyList(),
    val targetUserIds: List<UUID>  = emptyList()
)
