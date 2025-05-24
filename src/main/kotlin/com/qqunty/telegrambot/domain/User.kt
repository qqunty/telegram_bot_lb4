package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id val id: UUID = UUID.randomUUID(),
    val chatId: String,

    @ManyToMany
    @JoinTable(
        name = "user_groups",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    val roles: MutableSet<Group> = mutableSetOf()
) {
    constructor(chatId: String) : this(UUID.randomUUID(), chatId)
}
