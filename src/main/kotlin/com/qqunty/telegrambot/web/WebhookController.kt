package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.service.NotificationService
import com.qqunty.telegrambot.web.dto.ImmediateEventDto     // ③
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/webhook")
class WebhookController(private val service: NotificationService) {

    @PostMapping("/immediate")
    fun onImmediate(@RequestBody dto: ImmediateEventDto) {  // ④
        service.handleImmediateEvent(dto)
    }
}
