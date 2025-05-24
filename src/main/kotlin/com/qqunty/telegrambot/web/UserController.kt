// src/main/kotlin/com/qqunty/telegrambot/web/UserController.kt
package com.qqunty.telegrambot.web


import com.qqunty.telegrambot.util.toDto
import com.qqunty.telegrambot.util.toEntity
import com.qqunty.telegrambot.repository.UserRepository
import com.qqunty.telegrambot.util.toDto
import com.qqunty.telegrambot.util.toEntity
import com.qqunty.telegrambot.web.dto.UserDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(private val repo: UserRepository) {

    @GetMapping fun all() = repo.findAll().map { it.toDto() }

    @GetMapping("/{id}")
    fun one(@PathVariable id: UUID) = repo.findById(id).orElseThrow().toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody dto: UserDto) = repo.save(dto.toEntity()).toDto()

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody dto: UserDto) =
        repo.save(dto.copy(id = id).toEntity()).toDto()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) = repo.deleteById(id)
}
