package com.nextcommerce.backendv2.services

import com.nextcommerce.backendv2.models.User
import com.nextcommerce.backendv2.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun save(user: User): User {
        return this.userRepository.save(user)
    }

    fun findByEmail(email: String): User? {
        return this.userRepository.findByEmail(email)
    }

    fun findById(id: Int): Optional<User> {
        return this.userRepository.findById(id)
    }
}