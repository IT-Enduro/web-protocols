package ru.romanow.protocols.rest.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.romanow.protocols.api.model.PingResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@Tag(name = "Ping controller")
@RequestMapping("/api/v1")
class PingController {
    private val logger = LoggerFactory.getLogger(PingController::class.java)

    @Operation(
        description = "Ping",
        responses = [ApiResponse(
            description = "OK",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = PingResponse::class))]
        )]
    )
    @GetMapping(value = ["/ping"], produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun ping(): PingResponse {
        logger.info("Request to '/api/v1/ping'")
        return PingResponse("OK")
    }

    @Operation(
        description = "Set cookies",
        responses = [ApiResponse(
            description = "OK",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = PingResponse::class))]
        )]
    )
    @GetMapping(value = ["/cookies"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun setCookies(response: HttpServletResponse): PingResponse {
        logger.info("Request to '/cookies'")
        response.addCookie(createCookie(COOKIE_NAME, RandomStringUtils.randomAlphabetic(5)))
        return PingResponse("OK")
    }

    private fun createCookie(cookieName: String, cookieValue: String): Cookie {
        val cookie = Cookie(cookieName, cookieValue)
        cookie.path = "/"
        return cookie
    }

    companion object {
        private const val COOKIE_NAME = "TestCookie"
    }
}