package com.nextcommerce.backendv2.repositories

import com.nextcommerce.backendv2.models.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StoreRepository : JpaRepository<Store, Int> {
    @Query("SELECT s.id FROM Store s WHERE s.userId = :userId ORDER BY s.createdAt ASC LIMIT 1")
    fun findOldestStoreIdByUserId(userId: Int): Int?

    @Query("SELECT s.id, s.name FROM Store s WHERE s.userId = :userId ORDER BY s.createdAt ASC")
    fun getStoreNames(userId: Int): List<Any>
}
