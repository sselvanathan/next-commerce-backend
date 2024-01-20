package com.nextcommerce.backendv2.services

import com.nextcommerce.backendv2.models.Billboard
import com.nextcommerce.backendv2.models.Store
import com.nextcommerce.backendv2.repositories.BillboardRepository
import org.springframework.stereotype.Service

@Service
class BillboardService(private val billboardRepository: BillboardRepository) {

    fun getBillboards(userId: Int): List<Any> {
        //ToDo hash id
        val storeNames = this.billboardRepository.getBillboards(userId)

        return storeNames
    }
}