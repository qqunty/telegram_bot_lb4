package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.repository.GroupRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/web/groups")
class GroupControllerWeb(
    private val groupRepo: GroupRepository
) {
    @GetMapping fun list() = groupRepo.findAll()
    @PostMapping fun create(@RequestBody dto: CreateGroupDto): Group {
        val g = com.qqunty.telegrambot.domain.Group(name=dto.name, description=dto.description)
        return groupRepo.save(g)
    }
}

data class CreateGroupDto(val name: String, val description: String?)
