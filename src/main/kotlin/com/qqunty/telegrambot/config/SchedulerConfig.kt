package com.qqunty.telegrambot.config

import org.quartz.spi.JobFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.scheduling.quartz.SpringBeanJobFactory

@Configuration
class SchedulerConfig {

    /** JobFactory, который позволяет @Autowired внутри Quartz-Job’ов */
    @Bean
    fun jobFactory(ctx: ApplicationContext): JobFactory =
        object : SpringBeanJobFactory() {
            init { setApplicationContext(ctx) }
        }

    /** Планировщик (RAMJobStore по умолчанию). */
    @Bean
    fun schedulerFactoryBean(jobFactory: JobFactory): SchedulerFactoryBean =
        SchedulerFactoryBean().apply {
            setJobFactory(jobFactory)
            setOverwriteExistingJobs(true)
            setSchedulerName("telegramBotScheduler")   // ← вызов метода
            // setDataSource(dataSource)  // если решите перейти на JDBC-Store
        }
}
