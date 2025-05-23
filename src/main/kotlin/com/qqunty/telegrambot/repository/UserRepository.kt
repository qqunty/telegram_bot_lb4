package com.qqunty.telegrambot.repository

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    /** Найти всех пользователей, у которых в ролях есть указанная группа */
    fun findAllByRolesContains(group: Group): List<User>
}
