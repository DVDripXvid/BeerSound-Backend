package com.beersound.beersoundbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BeersoundBackendApplication

fun main(args: Array<String>) {
    runApplication<BeersoundBackendApplication>(*args)
}
