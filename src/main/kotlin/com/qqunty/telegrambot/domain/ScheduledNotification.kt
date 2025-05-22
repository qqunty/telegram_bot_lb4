package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "scheduled_notifications")
data class ScheduledNotification(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    val template: Template,

    @Column(nullable = false)
    val eventTime: Instant,

    @Column(nullable = false)
    val repeatCount: Int = 0,

    @Column(nullable = false)
    val repeatIntervalMinutes: Int = 0,

    @ManyToMany
    @JoinTable(
        name = "schedule_groups",
        joinColumns = [JoinColumn(name = "schedule_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    val targetGroups: Set<Group> = emptySet(),

    @ManyToMany
    @JoinTable(
        name = "schedule_users",
        joinColumns = [JoinColumn(name = "schedule_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val targetUsers: Set<User> = emptySet()
)
