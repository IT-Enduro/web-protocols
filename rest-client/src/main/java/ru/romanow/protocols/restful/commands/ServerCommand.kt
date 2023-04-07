package ru.romanow.protocols.restful.commands

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.shell.CompletionContext
import org.springframework.shell.CompletionProposal
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import org.springframework.shell.standard.ValueProvider
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.ServersResponse
import ru.romanow.protocols.api.model.StateInfo
import ru.romanow.protocols.restful.utils.prettyPrint


@ShellComponent
class CreateServerCommand(
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper,
) {
    private val logger = LoggerFactory.getLogger(CreateServerCommand::class.java)

    @ShellMethod(key = ["create"], value = "Create server", prefix = "-")
    fun create(@ShellOption(valueProvider = ServerTypeProvider::class) purpose: String) {
        val request = CreateServerRequest(
            purpose = purpose,
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

        logger.info("Created server with ID {}", serverId)
    }

    @ShellMethod(key = ["find-all"], value = "Get all servers")
    fun findAll() {
        val response = webClient.get()
            .uri("/api/v1/servers")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ServersResponse::class.java)
            .map { prettyPrint(it) }
            .block()!!

        logger.info("Get servers:\n{}", response)
    }

    @ShellMethod(key = ["find-by-id"], value = "Get server by Id")
    fun getById(@ShellOption id: Int) {
        val response = webClient
            .get()
            .uri("/api/v1/servers/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ServerResponse::class.java)
            .map { prettyPrint(it) }
            .onErrorResume { Mono.just(it.message!!) }
            .block()!!

        logger.info("Find server:\n{}", response)
    }

    @ShellMethod(key = ["find-in-city"], value = "Get servers in city")
    fun getById(@ShellOption city: String) {
        val response = webClient
            .get()
            .uri { it.path("/api/v1/servers").queryParam("city", city).build() }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ServersResponse::class.java)
            .map { prettyPrint(it) }
            .onErrorResume { Mono.just(it.message!!) }
            .block()!!

        logger.info("Find servers:\n{}", response)
    }

    @ShellMethod(key = ["update"], value = "Update server by Id")
    fun update(
        @ShellOption id: Int,
        @ShellOption(valueProvider = ServerTypeProvider::class, defaultValue = "empty") purpose: String?,
        @ShellOption(defaultValue = "") latency: Int?,
        @ShellOption(defaultValue = "") bandwidth: Int?,
        @ShellOption(defaultValue = "empty") city: String?,
        @ShellOption(defaultValue = "empty") country: String?,
    ) {
        val state = StateInfo(
            city = if (!city.equals("empty")) city else null,
            country = if (!country.equals("empty")) country else null)
        val request = CreateServerRequest(
            purpose = if (!purpose.equals("empty")) purpose else null,
            latency = latency,
            bandwidth = bandwidth,
            state = state
        )
        val response = webClient
            .patch()
            .uri("/api/v1/servers/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(ServerResponse::class.java)
            .map { prettyPrint(it) }
            .onErrorResume { Mono.just(it.message!!) }
            .block()!!

        logger.info("Updated server:\n{}", response)
    }
}

@Component
class ServerTypeProvider : ValueProvider {
    override fun complete(completionContext: CompletionContext): List<CompletionProposal> {
        return listOf("DATABASE", "BACKEND", "FRONTEND").map { CompletionProposal(it) }
    }
}