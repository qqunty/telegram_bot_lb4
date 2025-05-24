package com.qqunty.telegrambot.service

import org.quartz.*
import java.time.Instant
import java.util.*

object NotificationJobBuilder {

    fun buildJob(
        text: String,
        chatIds: Collection<Long>,
        fireAt: Instant,
        repeatMinutes: Int
    ): Pair<JobDetail, Trigger> {

        val data = JobDataMap().apply {
            put("text", text)
            put("targetChatIds", chatIds.toList())
        }

        val job = JobBuilder.newJob(NotificationJob::class.java)
            .withIdentity(UUID.randomUUID().toString())
            .usingJobData(data)
            .build()

        val trigger = TriggerBuilder.newTrigger()
            .forJob(job)
            .startAt(Date.from(fireAt))
            .withSchedule(
                if (repeatMinutes > 0)
                    SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(repeatMinutes)
                        .repeatForever()
                else
                    SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)
            )
            .build()

        return job to trigger
    }
}
