package com.qqunty.telegrambot.service

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component       
class NotificationJob(
    private val messageSender: MessageSender
) : Job {

    override fun execute(context: JobExecutionContext) {
        val text  = context.mergedJobDataMap.getString("text")
        val chats = context.mergedJobDataMap
                       .get("targetChatIds") as List<Long>

        chats.forEach { chatId -> messageSender.send(chatId, text) }
    }
}
