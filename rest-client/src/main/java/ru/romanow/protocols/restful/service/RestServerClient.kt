package ru.romanow.protocols.restful.service

import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.ServersResponse
import ru.romanow.protocols.api.model.StateInfo
import ru.romanow.protocols.common.client.service.ServerClient
import ru.romanow.protocols.restful.utils.prettyPrint

@Service
class RestServerClient(
    private val webClient: WebClient
) : ServerClient {

    override fun create(purpose: String): String {
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

        return location.path.split("/").last()
    }

    override fun findAll() =
        webClient.get()
            .uri("/api/v1/servers")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ServersResponse::class.java)
            .map { prettyPrint(it) }
            .block()!!

    override fun getById(id: Int) =
        webClient.get()
            .uri("/api/v1/servers/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ServerResponse::class.java)
            .map { prettyPrint(it) }
            .onErrorResume { Mono.just(it.message!!) }
            .block()!!

    override fun findInCity(city: String) =
        webClient.get()
            .uri { it.path("/api/v1/servers").queryParam("city", city).build() }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ServersResponse::class.java)
            .map { prettyPrint(it) }
            .onErrorResume { Mono.just(it.message!!) }
            .block()!!

    override fun update(
        id: Int, purpose: String?, latency: Int?, bandwidth: Int?, city: String?, country: String?
    ): String {
        val request = CreateServerRequest(
            purpose = purpose,
            latency = latency,
            bandwidth = bandwidth,
            state = StateInfo(city = city, country = country)
        )

        return webClient
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
    }
}