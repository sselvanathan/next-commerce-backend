package com.nextcommerce.backendv2.controllers

import com.nextcommerce.backendv2.dtos.Message
import com.nextcommerce.backendv2.services.StoreService
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/store")
class StoreController(private  val storeService: StoreService) {
    data class StoreIdResponse(val storeId: Int?)
    data class StoreResponse(val stores: List<Any>)
    @GetMapping("get/oldest/id")
    fun getOldestStoreId(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
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
            val oldestStoreId = this.storeService.getOldestStoreId(userId)

            return ResponseEntity.ok(StoreIdResponse(oldestStoreId))
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("expired"))
        }
    }

    @GetMapping("get/names")
    fun getStoreNames(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
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
            val stores = this.storeService.getStoreNames(userId)

            return ResponseEntity.ok(StoreResponse(stores))
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("expired"))
        }
    }

    @PostMapping("create")
    fun createStore(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
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
            val user = this.storeService.createStore("Test", userId)

            return ResponseEntity.ok(user)
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }
    }

    @GetMapping("debug/cookies")
    fun debugCookies(request: HttpServletRequest): ResponseEntity<String> {
        // Retrieve and log the raw Cookie header
        val cookieHeader = request.getHeader("Cookie") ?: "No Cookie header found"
        println("Raw Cookie Header: $cookieHeader")

        // Returning it for inspection, you might want to log it instead
        return ResponseEntity.ok(cookieHeader)
    }
}