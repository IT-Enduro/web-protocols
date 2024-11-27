package ru.romanow.protocols.soap

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SoapServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(SoapServerApplication::class.java, *args)
}
