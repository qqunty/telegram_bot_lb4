package com.qqunty.telegrambot.service

import com.qqunty.telegrambot.domain.ScheduledNotification
import org.quartz.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class NotificationJobBuilder {

    fun buildJob(notification: ScheduledNotification): JobDetail {
        val jobData = JobDataMap().apply {
            put("notificationId", notification.id.toString())
            put("templateId", notification.templateId.toString())

            val chatIds = notification.targetChatIds.mapNotNull { it.toLongOrNull() }
            put("chatIds", chatIds.toLongArray())
        }

        return JobBuilder.newJob(NotificationJob::class.java)
            .withIdentity(JobKey.jobKey("job-${notification.id}"))
            .usingJobData(jobData)
            .storeDurably()
            .build()
    }

    fun buildTrigger(notification: ScheduledNotification): Trigger {
        val startAt = Date.from(notification.eventTime)
        return TriggerBuilder.newTrigger()
            .withIdentity(TriggerKey.triggerKey("trigger-${notification.id}"))
            .startAt(startAt)
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withRepeatCount(notification.repeatCount)
                    .withIntervalInMinutes(notification.repeatIntervalMinutes)
            )
            .forJob("job-${notification.id}")
            .build()
    }
}
