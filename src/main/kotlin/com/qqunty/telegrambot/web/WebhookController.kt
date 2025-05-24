package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.service.NotificationService
import com.qqunty.telegrambot.web.dto.ImmediateEventDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/webhook")
class WebhookController(
    private val notificationService: NotificationService
) {

    @PostMapping
    fun handleImmediateEvent(@RequestBody dto: ImmediateEventDto) {
        notificationService.notifyImmediate(dto)
    }
}
