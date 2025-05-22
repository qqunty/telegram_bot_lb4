package com.qqunty.telegrambot.repository

import com.qqunty.telegrambot.domain.ScheduledNotification
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ScheduledNotificationRepository : JpaRepository<ScheduledNotification, UUID>
