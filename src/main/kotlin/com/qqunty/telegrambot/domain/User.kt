package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true, nullable = false)
    val chatId: String,

    // ВАЖНО: fetch = EAGER, чтобы коллекция roles загружалась сразу вместе с пользователем
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_groups",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    val roles: MutableSet<Group> = mutableSetOf()
) {
    // нужен конструктор без аргументов для JPA
    constructor() : this(UUID.randomUUID(), "", mutableSetOf())
}
