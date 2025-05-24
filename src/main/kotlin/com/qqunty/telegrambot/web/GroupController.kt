// src/main/kotlin/com/qqunty/telegrambot/web/GroupController.kt
package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.repository.GroupRepository
import com.qqunty.telegrambot.repository.UserRepository
import com.qqunty.telegrambot.util.toDto
import com.qqunty.telegrambot.util.toEntity
import com.qqunty.telegrambot.web.dto.GroupDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/groups")
class GroupController(
    private val groupRepo: GroupRepository,
    private val userRepo:  UserRepository
) {

    @GetMapping
    fun all(): List<GroupDto> =
        groupRepo.findAll().map { it.toDto() }

    @GetMapping("/{id}")
    fun one(@PathVariable id: UUID): GroupDto =
        groupRepo.findById(id)
            .orElseThrow { IllegalArgumentException("Группа $id не найдена") }
            .toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody dto: GroupDto): GroupDto {
        val entity = dto.toEntity()
        return groupRepo.save(entity).toDto()
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody dto: GroupDto
    ): GroupDto {
        val entity = dto.copy(id = id).toEntity()
        return groupRepo.save(entity).toDto()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) =
        groupRepo.deleteById(id)

    /**
     * Добавляет пользователя в группу.
     */
    @PutMapping("/{groupId}/users/{userId}")
    fun addUserToGroup(
        @PathVariable groupId: UUID,
        @PathVariable userId: UUID
    ): GroupDto {
        val group = groupRepo.findById(groupId)
            .orElseThrow { IllegalArgumentException("Группа $groupId не найдена") }
        val user = userRepo.findById(userId)
            .orElseThrow { IllegalArgumentException("Пользователь $userId не найден") }

        user.roles.add(group)
        userRepo.save(user)

        // возвращаем обновлённый объект группы
        return groupRepo.findById(groupId).get().toDto()
    }
}
