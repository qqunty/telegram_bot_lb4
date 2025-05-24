package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "groups")
class Group(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, unique = true)
    val name: String,

    val description: String? = null,

    /** Обратная сторона Many-to-Many */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    val users: MutableSet<User> = mutableSetOf()
)
