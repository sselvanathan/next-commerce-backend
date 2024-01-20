package com.nextcommerce.backendv2.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "billboards")
open class Billboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column(name = "store_id")
    var storeId: Int = 0

    @Column(name = "user_id")
    var userId: Int = 0

    @Column(name = "label")
    var label: String = ""

    @Column(name = "isFeatured")
    var isFeatured: Boolean = false

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null


    @PrePersist
    fun prePersist() {
        createdAt = LocalDateTime.now()
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}