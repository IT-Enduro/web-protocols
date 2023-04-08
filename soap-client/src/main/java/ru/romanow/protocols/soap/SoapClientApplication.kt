package ru.romanow.protocols.soap

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import ru.romanow.protocols.soap.properties.ServiceUrlProperties

@SpringBootApplication
@EnableConfigurationProperties(ServiceUrlProperties::class)
class SoapClientApplication

fun main(args: Array<String>) {
    SpringApplication.run(SoapClientApplication::class.java, *args)
}