package ru.romanow.protocols.restful

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class RestClientApplication

fun main(args: Array<String>) {
    SpringApplication.run(RestClientApplication::class.java, *args)
}
