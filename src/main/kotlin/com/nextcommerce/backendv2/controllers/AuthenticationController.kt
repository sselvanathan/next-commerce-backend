package com.nextcommerce.backendv2.controllers

import com.nextcommerce.backendv2.dtos.Message
import com.nextcommerce.backendv2.services.UserService
import com.nextcommerce.backendv2.dtos.RegisterDTO
import com.nextcommerce.backendv2.models.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        cookie.path = "/"
        //cookie.secure = true ToDO change if prod

        response.addCookie(cookie)

        return ResponseEntity.ok(Message("success"))
    }

    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0
        cookie.path = "/"

        response.addCookie(cookie)

        return ResponseEntity.ok(Message("success"))
    }
}