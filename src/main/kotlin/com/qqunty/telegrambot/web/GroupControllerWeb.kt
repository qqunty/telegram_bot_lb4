package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.repository.GroupRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/groups")
class GroupControllerWeb(
    private val repo: GroupRepository
) {
    @GetMapping fun list(): List<Group> = repo.findAll()
    @PostMapping fun create(@RequestBody dto: CreateGroupDto): Group {
        val g = Group(name = dto.name, description = dto.description)
        return repo.save(g)
    }
}
data class CreateGroupDto(val name: String, val description: String?)
