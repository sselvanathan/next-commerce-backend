package com.nextcommerce.backendv2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class NextCommerceBackendV2Application

fun main(args: Array<String>) {
    runApplication<NextCommerceBackendV2Application>(*args)
}
