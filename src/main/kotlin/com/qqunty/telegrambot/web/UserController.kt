package com.qqunty.telegrambot.web

import com.qqunty.telegrambot.domain.User
import com.qqunty.telegrambot.repository.UserRepository
import com.qqunty.telegrambot.web.dto.CreateUserDto
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val repo: UserRepository
) {
    @GetMapping fun list(): List<User> = repo.findAll()
    @PostMapping fun create(@RequestBody dto: CreateUserDto): User =
        repo.save(User(chatId = dto.chatId))
}
