package com.qqunty.telegrambot.repository

import com.qqunty.telegrambot.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByChatId(chatId: String): User?
}
