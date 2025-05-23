package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.User
import com.qqunty.telegrambot.repository.UserRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepo: UserRepository
) {
    @GetMapping
    fun list(): List<User> = userRepo.findAll()

    @PostMapping
    fun create(@RequestBody dto: CreateUserDto): User =
        userRepo.save(User(chatId = dto.chatId))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = userRepo.deleteById(id)
}

data class CreateUserDto(val chatId: String)
