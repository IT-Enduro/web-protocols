package ru.romanow.protocols.producer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ProducerApplication

fun main(args: Array<String>) {
    SpringApplication.run(ProducerApplication::class.java, *args)
}
