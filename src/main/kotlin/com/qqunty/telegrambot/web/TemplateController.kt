package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.Template
import com.qqunty.telegrambot.domain.EventType
import com.qqunty.telegrambot.domain.Channel
import com.qqunty.telegrambot.repository.TemplateRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/web/templates")
class TemplateController(private val tplRepo: TemplateRepository) {
    @GetMapping fun list() = tplRepo.findAll()
    @PostMapping fun create(@RequestBody dto: CreateTemplateDto): Template {
        val t = Template(
            eventType = dto.eventType,
            channel   = dto.channel,
            text      = dto.text
        )
        return tplRepo.save(t)
    }
}

data class CreateTemplateDto(
    val eventType: EventType,
    val channel: Channel,
    val text: String
)
