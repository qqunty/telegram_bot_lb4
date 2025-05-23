package com.qqunty.telegrambot.repository

import com.qqunty.telegrambot.domain.EventType
import com.qqunty.telegrambot.domain.Template
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TemplateRepository : JpaRepository<Template, UUID> {
    fun findByEventType(type: EventType): Template?
}
