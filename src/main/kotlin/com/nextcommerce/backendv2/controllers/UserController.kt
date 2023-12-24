package com.nextcommerce.backendv2.controllers

import com.nextcommerce.backendv2.dtos.Message
import com.nextcommerce.backendv2.services.UserService
import io.jsonwebtoken.Jwts
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/user")
class UserController(private  val userService: UserService) {
    @GetMapping("details")
    fun getUserDetails(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        try {
            if (jwt == null){
                return ResponseEntity.status(401).body(Message("unauthenticated"))
            }

            val body = Jwts
                .parser()
                .setSigningKey("secret") // ToDo env
                .parseClaimsJws(jwt)
                .body

            val userId = body.issuer.toInt()
            val user = this.userService.findById(userId)

            return ResponseEntity.ok(user)
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }
    }
}