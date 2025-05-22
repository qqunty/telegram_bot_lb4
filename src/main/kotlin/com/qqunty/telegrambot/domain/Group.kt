package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "groups")
data class Group(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, unique = true)
    val name: String,

    val description: String? = null,

    @ManyToMany(mappedBy = "roles")
    val users: Set<User> = emptySet()
)
