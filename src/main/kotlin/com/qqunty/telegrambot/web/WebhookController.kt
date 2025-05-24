package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.EventType
import com.qqunty.telegrambot.domain.Template
import com.qqunty.telegrambot.domain.TemplateChannel
import com.qqunty.telegrambot.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class WebhookPayload(
    val eventType: EventType,
    val link: String?,
    val place: String?,
    val time: String?
)

@RestController
@RequestMapping("/api/webhook")
class WebhookController(
    private val notificationService: NotificationService
) {
    @PostMapping
    fun handle(@RequestBody payload: WebhookPayload): ResponseEntity<Void> {
        val chatIds = listOf<String>()
        val data = mapOf(
            "link" to (payload.link ?: ""),
            "place" to (payload.place ?: ""),
            "time" to (payload.time ?: "")
        )
        val tpl = Template( 
            eventType = payload.eventType,
            channel   = TemplateChannel.BOTH,
            text      = "{{link}}"
        )
        notificationService.sendImmediate(tpl, chatIds, data)
        return ResponseEntity.ok().build()
    }
}
