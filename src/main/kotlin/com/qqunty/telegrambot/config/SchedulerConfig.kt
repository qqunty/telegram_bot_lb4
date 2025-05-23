package com.qqunty.telegrambot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class SchedulerConfig {

    @Bean
    fun taskScheduler() = ThreadPoolTaskScheduler().apply {
        poolSize = 4
        setThreadNamePrefix("scheduler-")
        initialize()
    }
}

@Bean
fun objectMapper(): ObjectMapper = ObjectMapper().findAndRegisterModules()