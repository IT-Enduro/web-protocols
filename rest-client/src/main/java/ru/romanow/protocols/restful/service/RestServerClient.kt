package ru.romanow.protocols.restful.service

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono
import ru.romanow.protocols.api.model.*
import ru.romanow.protocols.common.client.service.ServerClient
import ru.romanow.protocols.restful.utils.prettyPrint

@Service
@Suppress("UNUSED_PARAMETER")
class RestServerClient(
    private val webClient: WebClient
) : ServerClient {

    override fun create(purpose: String): String {
        val request = CreateServerRequest().apply {
            this.purpose = purpose
            latency = 10
            bandwidth = 1000
            state = StateInfo().apply {
                city = "Rostov"
                country = "Russia"
            }
        }

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

    @CircuitBreaker(name = "find-all", fallbackMethod = "findAllFallback")
    override fun findAll() =
        webClient.get()
            .uri("/api/v1/servers")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ServersResponse::class.java)
            .map { prettyPrint(it) }
            .block()!!

    @CircuitBreaker(name = "get-by-id", fallbackMethod = "getByIdFallback")
    override fun getById(id: Int) =
        webClient.get()
            .uri("/api/v1/servers/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ServerResponse::class.java)
            .map { prettyPrint(it) }
            .onErrorResume { Mono.just(it.message!!) }
            .block()!!

    @CircuitBreaker(name = "find-in-city", fallbackMethod = "findInCityFallback")
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
        val request = UpdateServerRequest().apply {
            this.purpose = purpose
            this.latency = latency
            this.bandwidth = bandwidth
            state = StateInfo().apply {
                this.city = city
                this.country = country
            }
        }

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

    override fun delete(id: Int) {
        webClient
            .delete()
            .uri("/api/v1/servers/{id}", id)
            .retrieve()
            .bodyToMono(Void::class.java)
            .block()
    }

    private fun findAllFallback(exception: Exception) = "[]"
    private fun findInCityFallback(city: String, exception: Exception) = "[]"
    private fun getByIdFallback(id: Int, exception: Exception) = "{}"
}
