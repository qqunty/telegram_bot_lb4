package com.qqunty.telegrambot.config

import org.quartz.spi.JobFactory
import org.quartz.spi.TriggerFiredBundle
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.scheduling.quartz.SpringBeanJobFactory

@Configuration
class SchedulerConfig : ApplicationContextAware {

    private lateinit var ctx: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        ctx = applicationContext
    }

    @Bean
    fun jobFactory(): JobFactory =
        object : SpringBeanJobFactory() {

            override fun createJobInstance(bundle: TriggerFiredBundle): Any {
                val job = super.createJobInstance(bundle)
                ctx.autowireCapableBeanFactory.autowireBean(job)
                return job
            }
        }

    @Bean
    fun schedulerFactory(jobFactory: JobFactory): SchedulerFactoryBean =
        SchedulerFactoryBean().apply {
            setSchedulerName("telegramBotScheduler")   
            setJobFactory(jobFactory)
            setOverwriteExistingJobs(true)
        }
}
