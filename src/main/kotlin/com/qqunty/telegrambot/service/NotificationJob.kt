package com.qqunty.telegrambot.service

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class NotificationJob(
  private val notificationService: NotificationService
) : Job {
  override fun execute(context: JobExecutionContext) {
    @Suppress("UNCHECKED_CAST")
    val payload = context.mergedJobDataMap.get("payload") as Map<String, Any>
    notificationService.handleImmediateEvent(payload)
  }
}
