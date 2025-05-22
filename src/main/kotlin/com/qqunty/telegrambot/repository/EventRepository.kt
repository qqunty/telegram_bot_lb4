package com.qqunty.telegrambot.repository

import com.qqunty.telegrambot.domain.Event
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventRepository : JpaRepository<Event, UUID>
