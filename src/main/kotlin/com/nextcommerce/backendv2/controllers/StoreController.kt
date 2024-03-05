package com.nextcommerce.backendv2.controllers

import com.nextcommerce.backendv2.dtos.Message
import com.nextcommerce.backendv2.services.StoreService
import io.jsonwebtoken.Jwts
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/store")
class StoreController(private  val storeService: StoreService) {
    data class StoreIdResponse(val storeId: Int?)
    data class StoreResponse(val stores: List<Any>)
@GetMapping("get/oldest/id")
fun getOldestStoreId(@RequestHeader("Authorization") authHeader: String?): ResponseEntity<Any> {
    try {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }

        // Extract the JWT from the Authorization header
        val jwt = authHeader.substring(7) // Remove "Bearer " prefix

        val body = Jwts
            .parser()
            .setSigningKey("secret") // ToDo env
            .parseClaimsJws(jwt)
            .body

        val userId = body.issuer.toInt()
        val oldestStoreId = this.storeService.getOldestStoreId(userId)

        return ResponseEntity.ok(StoreIdResponse(oldestStoreId))
    } catch (e: Exception) {
        return ResponseEntity.status(401).body(Message("expired or invalid token"))
    }
}

    @GetMapping("get/names")
    fun getStoreNames(@RequestHeader("Authorization") authHeader: String?): ResponseEntity<Any> {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(Message("unauthenticated"))
            }

            // Extract the JWT from the Authorization header
            val jwt = authHeader.substring(7) // Remove "Bearer " prefix

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
    fun createStore(@RequestHeader("Authorization") authHeader: String?): ResponseEntity<Any> {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(Message("unauthenticated"))
            }

            // Extract the JWT from the Authorization header
            val jwt = authHeader.substring(7) // Remove "Bearer " prefix

            val body = Jwts
                .parser()
                .setSigningKey("secret") // ToDo env
                .parseClaimsJws(jwt)
                .body

            val userId = body.issuer.toInt()
            val store = this.storeService.createStore(userId, "Test") //ToDo

            return ResponseEntity.ok(StoreIdResponse(store.id))
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }
    }
}