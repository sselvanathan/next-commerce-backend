package com.nextcommerce.backendv2.controllers

import com.nextcommerce.backendv2.dtos.Message
import com.nextcommerce.backendv2.models.Billboard
import com.nextcommerce.backendv2.models.Store
import com.nextcommerce.backendv2.services.BillboardService
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
@RequestMapping("api/billboard")
class BillboardController(private  val billboardService: BillboardService) {
    data class StoreIdResponse(val stores: Store?)
    @GetMapping("get/stores")
    fun getBillboards(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
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
            val billboards = this.billboardService.getBillboards(userId)

            return ResponseEntity.ok(billboards)
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("expired"))
        }
    }
}