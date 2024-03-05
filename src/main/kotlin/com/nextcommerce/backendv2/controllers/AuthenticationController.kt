package com.nextcommerce.backendv2.controllers

import com.nextcommerce.backendv2.dtos.Message
import com.nextcommerce.backendv2.services.UserService
import com.nextcommerce.backendv2.dtos.RegisterDTO
import com.nextcommerce.backendv2.models.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/auth")
class AuthenticationController(private  val userService: UserService) {

    @PostMapping("register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<User> {
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password

        return ResponseEntity.ok(this.userService.save(user))
    }

    @PostMapping("login")
    fun login(@RequestBody body: RegisterDTO, response: HttpServletResponse): ResponseEntity<Any> {
        val user = this.userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(Message("User not found!"))

        if (!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body(Message("invalid Password!"))
        }

        val issuer = user.id.toString()
        val expirationDate = Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000) // 1 Day

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, "secret") //ToDo save in env
            .compact()


            // Constructing user JSON object
    val userJson = mapOf(
        "id" to user.id.toString(),
        "name" to user.name, // Assuming your user object has a name field
        "email" to user.email,
        "jwt" to jwt // Including JWT in the user object
    )

        return ResponseEntity.ok(userJson)
    }

    @PostMapping("logout")
    fun logout(@RequestHeader("Authorization") authHeader: String?): ResponseEntity<Any> {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }

        // Extract the JWT from the Authorization header
        val jwt = authHeader.substring(7) // Remove "Bearer " prefix

        //ToDo check if token is still valid and add it to a token blacklist

        return ResponseEntity.ok(Message("Successfully logged out!"))
    }
}