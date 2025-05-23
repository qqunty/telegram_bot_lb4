package com.qqunty.telegrambot.config

import org.quartz.spi.TriggerFiredBundle
import org.springframework.beans.factory.AutowireCapableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SpringBeanJobFactory
import org.springframework.scheduling.quartz.SchedulerFactoryBean

/**
 * Настройка Quartz-планировщика: внедрение Spring-бинов в Quartz-Job.
 */
@Configuration
class SchedulerConfig(
    private val beanFactory: AutowireCapableBeanFactory
) {

    /** JobFactory, которая даёт Quartz возможность пользоваться DI Spring */
    private class AutowiringJobFactory(
        private val factory: AutowireCapableBeanFactory
    ) : SpringBeanJobFactory() {

        override fun setBeanFactory(factory: AutowireCapableBeanFactory) {
            super.setBeanFactory(factory)      // обязательно — переданный параметр
        }

        override fun createJobInstance(bundle: TriggerFiredBundle): Any =
            super.createJobInstance(bundle).also { factory.autowireBean(it) }
    }

    @Bean
    fun jobFactory(): SpringBeanJobFactory = AutowiringJobFactory(beanFactory)

    @Bean
    fun schedulerFactory(jobFactory: SpringBeanJobFactory): SchedulerFactoryBean =
        SchedulerFactoryBean().apply {
            setJobFactory(jobFactory)
            isOverwriteExistingJobs = true
        }
}
