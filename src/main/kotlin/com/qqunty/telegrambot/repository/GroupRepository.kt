package com.qqunty.telegrambot.repository

import com.qqunty.telegrambot.domain.Group
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupRepository : JpaRepository<Group, UUID>
