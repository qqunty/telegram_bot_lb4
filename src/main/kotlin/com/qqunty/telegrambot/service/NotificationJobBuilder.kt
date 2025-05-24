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

    /**
     * Строит пару (JobDetail, Trigger) для планировщика Quartz
     */
    fun build(sn: ScheduledNotification): Pair<JobDetail, Trigger> {
        // Передаём в данные джоба текст и список чатов
        val data = JobDataMap().apply {
            put("text", sn.template.text)
            put("targetChatIds", sn.targetChatIds.toList())
        }

        // Деталь джоба с уникальным идентификатором
        val job: JobDetail = JobBuilder.newJob(NotificationJob::class.java)
            .withIdentity(sn.id.toString())
            .usingJobData(data)
            .build()

        // Триггер, стартующий в sn.eventTime и с повторами, если нужно
        val trigger: Trigger = TriggerBuilder.newTrigger()
            .forJob(job)
            .startAt(Date.from(sn.eventTime.atZone(ZoneId.systemDefault()).toInstant()))
            .withSchedule(
                if (sn.repeatIntervalMinutes > 0) {
                    SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(sn.repeatIntervalMinutes)
                        .repeatForever()
                } else {
                    // без повторов (одноразово)
                    SimpleScheduleBuilder.simpleSchedule()
                        .withRepeatCount(0)
                }
            )
            .build()

        return job to trigger
    }
}
