package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.Group
import com.qqunty.telegrambot.repository.GroupRepository
import com.qqunty.telegrambot.web.dto.GroupDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/groups")
class GroupController(
    private val groupRepo: GroupRepository
) {

    @GetMapping
    fun list(): List<Group> = groupRepo.findAll()

    @PostMapping
    fun create(@RequestBody dto: GroupDto): ResponseEntity<Group> {
        val g = Group(name = dto.name, description = dto.description ?: "")
        return ResponseEntity.ok(groupRepo.save(g))
    }
}
