package com.nextcommerce.backendv2.repositories

import com.nextcommerce.backendv2.models.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BillboardRepository : JpaRepository<Store, Int> {
    @Query("SELECT b.id, b.name FROM billboards b WHERE s.userId = :userId ORDER BY s.createdAt DESC")
    fun getBillboards(userId: Int): List<Any>
}
