package com.nextcommerce.backendv2.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
@Table(name = "_user")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column(unique = true)
    var email = ""

    @Column
    var name: String = ""

    @Column
    var password = ""
        @JsonIgnore
        get
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    fun comparePassword(password: String): Boolean {
        val passwordEncoder = BCryptPasswordEncoder()

        return passwordEncoder.matches(password, this.password)
    }
}