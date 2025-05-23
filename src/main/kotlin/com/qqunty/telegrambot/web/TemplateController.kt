package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.Template
import com.qqunty.telegrambot.repository.TemplateRepository
import com.qqunty.telegrambot.web.dto.TemplateDto
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/templates")
class TemplateController(
    private val repo: TemplateRepository
) {
    @GetMapping fun list(): List<Template> = repo.findAll()
    @PostMapping fun create(@RequestBody dto: TemplateDto): Template =
        repo.save(Template(eventType = dto.eventType, channel = dto.channel, text = dto.text))
}
