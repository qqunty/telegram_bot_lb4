package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.dto.ImmediateEventDto
import com.qqunty.telegrambot.service.NotificationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/webhook")
class WebhookController(
    private val notificationService: NotificationService
) {

    @PostMapping
    fun receive(@RequestBody dto: ImmediateEventDto) {
        notificationService.handleImmediateEvent(dto)
    }
}
