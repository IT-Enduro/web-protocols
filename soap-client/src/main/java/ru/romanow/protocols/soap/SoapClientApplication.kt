package ru.romanow.protocols.soap

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import ru.romanow.protocols.soap.web.WebServiceClient

@SpringBootApplication
class SoapClientApplication {

    @Bean
    @Profile("!test")
    fun runner(clients: List<WebServiceClient>): ApplicationRunner {
        return ApplicationRunner {
            clients.forEach { it.makeRequest() }
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(SoapClientApplication::class.java, *args)
}