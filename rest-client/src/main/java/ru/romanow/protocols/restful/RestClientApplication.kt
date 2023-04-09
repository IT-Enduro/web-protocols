package ru.romanow.protocols.restful

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction.ofResponseProcessor
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.romanow.protocols.api.model.ErrorResponse
import ru.romanow.protocols.api.model.ValidationErrorResponse
import ru.romanow.protocols.common.client.properties.ServiceUrlProperties
import ru.romanow.protocols.restful.utils.prettyPrint

@SpringBootApplication
class RestClientApplication {

    @Bean
    fun webClient(properties: ServiceUrlProperties): WebClient =
        WebClient
            .builder()
            .baseUrl(properties.serverUrl)
            .filter(ofResponseProcessor { exchangeFilterResponseProcessor(it) })
            .build()

    private fun exchangeFilterResponseProcessor(response: ClientResponse) =
        when (response.statusCode()) {
            NOT_FOUND -> response.bodyToMono(String::class.java)
                .flatMap { Mono.error(RuntimeException(prettyPrint(it, ErrorResponse::class.java))) }

            BAD_REQUEST -> response.bodyToMono(String::class.java)
                .flatMap { Mono.error(RuntimeException(prettyPrint(it, ValidationErrorResponse::class.java))) }

            else -> Mono.just(response)
        }
}

fun main(args: Array<String>) {
    SpringApplication.run(RestClientApplication::class.java, *args)
}