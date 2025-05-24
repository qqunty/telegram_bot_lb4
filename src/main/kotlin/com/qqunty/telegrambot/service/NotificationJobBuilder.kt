// src/main/kotlin/com/qqunty/telegrambot/service/NotificationJobBuilder.kt
package com.qqunty.telegrambot.service

import com.qqunty.telegrambot.domain.ScheduledNotification
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.util.Date

@Component
object NotificationJobBuilder {

    fun build(sn: ScheduledNotification): Pair<JobDetail, Trigger> {
        val template = requireNotNull(sn.template) {
            "ScheduledNotification.template не должен быть null"
        }
        val eventTime = requireNotNull(sn.eventTime) {
            "ScheduledNotification.eventTime не должен быть null"
        }

        val data = JobDataMap().apply {
            put("text", template.text)
            put("targetChatIds", sn.targetChatIds.toList())
        }

        val job: JobDetail = JobBuilder.newJob(NotificationJob::class.java)
            .withIdentity(sn.id.toString())
            .usingJobData(data)
            .build()

        val trigger: Trigger = TriggerBuilder.newTrigger()
            .forJob(job)
            .startAt(Date.from(eventTime.atZone(ZoneId.systemDefault()).toInstant()))
            .withSchedule(
                if (sn.repeatIntervalMinutes > 0) {
                    SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(sn.repeatIntervalMinutes)
                        .repeatForever()
                } else {
                    SimpleScheduleBuilder.simpleSchedule()
                        .withRepeatCount(0)
                }
            )
            .build()

        return job to trigger
    }
}
