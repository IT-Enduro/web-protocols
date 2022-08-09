package ru.romanow.protocols.producer.web

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.romanow.protocols.api.model.PingResponse

@RestController
@RequestMapping("/api/v1/ping")
class PingController {
    private val logger = LoggerFactory.getLogger(PingController::class.java)

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun ping(): PingResponse {
        logger.info("Request to '/api/v1/ping'")
        return PingResponse("OK")
    }
}