package ru.romanow.protocols.restful

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.Purpose
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.ServersResponse
import ru.romanow.protocols.api.model.StateInfo

@SpringBootApplication
class RestClientApplication {
    private val logger = LoggerFactory.getLogger(RestClientApplication::class.java)

    @Bean
    fun webClient(): WebClient = WebClient.create("http://localhost:8080")

    @Bean
    fun runner(webClient: WebClient, objectMapper: ObjectMapper): ApplicationRunner {
        return ApplicationRunner {
            val servers = webClient.get()
                .uri("/api/v1/servers")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ServersResponse::class.java)
                .block()!!

            logger.info("Get {} servers", servers.servers.size)

            var request = CreateServerRequest(
                purpose = Purpose.BACKEND.name,
                latency = 10,
                bandwidth = 10000,
                state = StateInfo(city = "Yerevan", country = "Armenia")
            )
            val location = webClient.post()
                .uri("/api/v1/servers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .toEntity<Void>()
                .map { it.headers.location }
                .block()!!

            val serverId = location.path.split("/").last()

            var server = webClient.get()
                .uri("/api/v1/servers/{id}", serverId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ServerResponse::class.java)
                .block()!!
            logger.info("Create new server '{}'", server)

            request = CreateServerRequest(purpose = Purpose.DATABASE.name)
            server = webClient.patch()
                .uri("/api/v1/servers/{id}", serverId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(ServerResponse::class.java)
                .block()!!

            logger.info("Update Server with id {}: '{}'", serverId, server)
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(RestClientApplication::class.java, *args)
}