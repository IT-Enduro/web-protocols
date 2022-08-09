package ru.romanow.protocols.consumer.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate

@Service
class PingServiceImpl(
    @Value("\${consumer.address}")
    private val consumerAddress: String,
    private val restTemplate: RestTemplate,
) : PingService {
    private val logger = LoggerFactory.getLogger(PingService::class.java)

    override fun ping(): Boolean {
        return try {
            return restTemplate.getForObject(consumerAddress + PING_PATH, Boolean::class.java)!!
        } catch (exception: RestClientResponseException) {
            logger.warn(
                "Request to '{}' finish with error {}:{}", PING_PATH,
                exception.rawStatusCode, exception.statusText
            )
            false
        }
    }

    companion object {
        private const val PING_PATH = "/api/v1/ping"
    }
}