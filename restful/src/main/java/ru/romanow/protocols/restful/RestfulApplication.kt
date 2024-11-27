package ru.romanow.protocols.restful

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class RestServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(RestServerApplication::class.java, *args)
}
