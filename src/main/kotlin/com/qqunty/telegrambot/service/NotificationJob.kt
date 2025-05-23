package com.qqunty.telegrambot.service

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * Quartz-задача, которая по расписанию дергает NotificationService.
 */
@Component
class NotificationJob : Job {

  @Autowired
  private lateinit var notificationService: NotificationService

  override fun execute(ctx: JobExecutionContext) {
    ctx.jobDetail.jobDataMap.getString("scheduleId")
      ?.let { notificationService.sendScheduled(UUID.fromString(it)) }
  }
}
