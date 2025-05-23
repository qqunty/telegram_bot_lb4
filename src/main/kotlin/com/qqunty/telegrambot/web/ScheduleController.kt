package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.ScheduledNotification
import com.qqunty.telegrambot.service.NotificationService
import com.qqunty.telegrambot.web.dto.ScheduleDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/schedules")
class ScheduleController(
  private val notificationService: NotificationService
) {
  @PostMapping
  fun create(@RequestBody dto: ScheduleDto): ScheduledNotification =
    notificationService.schedule(dto)

  @GetMapping
  fun listAll(): List<ScheduledNotification> =
    notificationService.listAll()
}
