package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.EventType
import com.qqunty.telegrambot.domain.Template
import com.qqunty.telegrambot.domain.TemplateChannel
import com.qqunty.telegrambot.repository.TemplateRepository
import org.springframework.web.bind.annotation.*
import java.util.*

data class CreateTemplateRequest(
    val eventType: EventType,
    val channel: TemplateChannel,
    val text: String
)

@RestController
@RequestMapping("/api/templates")
class TemplateController(
    private val templateRepo: TemplateRepository
) {

    @GetMapping
    fun listAll(): List<Template> =
        templateRepo.findAll()

    @PostMapping
    fun create(@RequestBody req: CreateTemplateRequest): Template {
        val tpl = Template(
            eventType = req.eventType,
            channel = req.channel,
            text = req.text
        )
        return templateRepo.save(tpl)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) {
        templateRepo.deleteById(id)
    }

    // при необходимости можно добавить PUT /{id} для редактирования
}
