package ru.romanow.protocols.soap

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SoapClientApplication

fun main(args: Array<String>) {
    SpringApplication.run(SoapClientApplication::class.java, *args)
}
