package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "groups")
class Group(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true)
    val name: String,

    val description: String? = null,

    @ManyToMany(mappedBy = "roles")
    val users: MutableSet<User> = mutableSetOf()
) {
    constructor() : this(UUID.randomUUID(), "", null, mutableSetOf())
}
