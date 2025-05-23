package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.service.NotificationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/webhook")
class WebhookController(
    private val svc: NotificationService
) {
    @PostMapping
    fun onEvent(@RequestBody payload: Map<String, Any>) {
        svc.handleImmediateEvent(payload)
    }
}
