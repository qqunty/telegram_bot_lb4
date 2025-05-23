package com.qqunty.telegrambot.service

import com.qqunty.telegrambot.domain.ScheduledNotification
import org.quartz.*
import java.util.*

object NotificationJobBuilder {

    data class JobBundle(val jobDetail: JobDetail, val trigger: Trigger)

    fun build(sn: ScheduledNotification): JobBundle {
        val data = JobDataMap().apply { put("scheduleId", sn.id.toString()) }

        val job = JobBuilder.newJob(NotificationJob::class.java)
            .withIdentity("notif-job-${sn.id}")
            .usingJobData(data)
            .build()

        val trigger = TriggerBuilder.newTrigger()
            .withIdentity("notif-trigger-${sn.id}")
            .startAt(Date.from(sn.eventTime))
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMinutes(sn.repeatIntervalMinutes)   // Int, без toLong()
                    .withRepeatCount(sn.repeatCount)
            )
            .forJob(job)
            .build()

        return JobBundle(job, trigger)
    }
}
