package com.qqunty.telegrambot.service

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.repository.GroupRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GroupService(
    private val repo: GroupRepository
) {
    fun listAll(): List<Group> = repo.findAll()
    fun find(id: UUID): Group = repo.findById(id).orElseThrow()
    fun save(group: Group): Group = repo.save(group)
    fun delete(id: UUID) = repo.deleteById(id)
}
