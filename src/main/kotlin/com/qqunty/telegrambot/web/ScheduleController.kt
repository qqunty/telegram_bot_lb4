package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.ScheduledNotification
import com.qqunty.telegrambot.service.NotificationService
import com.qqunty.telegrambot.web.dto.ScheduleDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/schedules")
class ScheduleController(
    private val notificationService: NotificationService
) {

    @PostMapping
    fun create(@RequestBody dto: ScheduleDto): ResponseEntity<ScheduledNotification> =
        ResponseEntity.ok(notificationService.schedule(dto))

    @GetMapping
    fun list(): List<ScheduledNotification> = notificationService.listAll()
}
