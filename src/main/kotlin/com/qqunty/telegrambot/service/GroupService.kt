package com.qqunty.telegrambot.service

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.repository.GroupRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GroupService(
    private val repo: GroupRepository
) {
    fun findAll(): List<Group> = repo.findAll()

    fun findById(id: UUID): Group =
        repo.findById(id).orElseThrow { NoSuchElementException("Group $id not found") }

    fun create(name: String, description: String?): Group {
        if (repo.findAll().any { it.name == name })
            throw IllegalArgumentException("Group '$name' already exists")
        return repo.save(Group(name = name, description = description))
    }

    fun delete(id: UUID) {
        if (!repo.existsById(id)) throw NoSuchElementException("Group $id not found")
        repo.deleteById(id)
    }
}
