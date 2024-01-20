package com.nextcommerce.backendv2.services

import com.nextcommerce.backendv2.models.Store
import com.nextcommerce.backendv2.repositories.StoreRepository
import org.springframework.stereotype.Service

@Service
class StoreService(private val storeRepository: StoreRepository) {

    fun createStore(userId: Int, name: String): Store {
        val newStore = Store()
        newStore.name = name
        newStore.userId = userId
        return storeRepository.save(newStore)
    }

    fun getOldestStoreId(userId: Int): Int? {
        //ToDo hash id
        val storeId = this.storeRepository.findOldestStoreIdByUserId(userId)

        return storeId
    }

    fun getStoreNames(userId: Int): List<Any> {
        //ToDo hash id
        val storeNames = this.storeRepository.getStoreNames(userId)

        return storeNames
    }
}