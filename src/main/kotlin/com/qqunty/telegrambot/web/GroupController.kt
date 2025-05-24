package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.repository.GroupRepository
import com.qqunty.telegrambot.repository.UserRepository
import com.qqunty.telegrambot.util.toDto
import com.qqunty.telegrambot.util.toEntity
import com.qqunty.telegrambot.web.dto.GroupDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID
import com.qqunty.telegrambot.util.toDto
import com.qqunty.telegrambot.util.toEntity


@RestController
@RequestMapping("/api/groups")
class GroupController(
    private val groupRepo: GroupRepository,
    private val userRepo: UserRepository
) {

    @GetMapping
    fun all(): List<GroupDto> =
        groupRepo.findAll().map { it.toDto() }

    @GetMapping("/{id}")
    fun one(@PathVariable id: UUID): GroupDto =
        groupRepo.findById(id).orElseThrow().toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody dto: GroupDto): GroupDto =
        groupRepo.save(dto.toEntity()).toDto()

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody dto: GroupDto
    ): GroupDto =
        groupRepo.save(dto.copy(id = id).toEntity()).toDto()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) =
        groupRepo.deleteById(id)

    /**
     * Назначить пользователя в группу
     */
    @PutMapping("/{groupId}/users/{userId}")
    fun addUser(
        @PathVariable groupId: UUID,
        @PathVariable userId: UUID
    ): GroupDto {
        val group = groupRepo.findById(groupId).orElseThrow()
        val user = userRepo.findById(userId).orElseThrow()
        group.users.add(user)
        return groupRepo.save(group).toDto()
    }
}
