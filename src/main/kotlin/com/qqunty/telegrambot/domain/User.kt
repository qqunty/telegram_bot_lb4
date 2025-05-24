package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User(

    @Id
    val id: UUID = UUID.randomUUID(),

    /** chatId в Telegram, может быть null */
    val chatId: String? = null,

    /** Группы (роли) пользователя */
    @ManyToMany
    @JoinTable(
        name = "user_groups",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    val roles: MutableSet<Group> = mutableSetOf()
)
