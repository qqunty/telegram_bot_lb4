// src/main/kotlin/com/qqunty/telegrambot/domain/User.kt
package com.qqunty.telegrambot.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue val id: UUID? = null,
    @Column(nullable = false, unique = true) val chatId: String,
    val firstName: String? = null,
    val lastName: String? = null
)
