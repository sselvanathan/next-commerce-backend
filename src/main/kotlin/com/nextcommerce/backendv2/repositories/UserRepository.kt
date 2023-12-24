package com.nextcommerce.backendv2.repositories

import com.nextcommerce.backendv2.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
}