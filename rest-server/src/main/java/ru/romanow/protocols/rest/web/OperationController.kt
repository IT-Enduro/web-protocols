package ru.romanow.protocols.rest.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.romanow.protocols.api.model.ErrorResponse
import ru.romanow.protocols.api.model.TestObjectRequest
import ru.romanow.protocols.api.model.TestObjectResponse
import ru.romanow.protocols.rest.exception.TooLowArgumentException

@RestController
@Tag(name = "Operation controller")
@RequestMapping("/api/v1/operation")
class OperationController {

    @Operation(
        summary = "Operation return simple response",
        responses = [ApiResponse(
            description = "OK",
            responseCode = "200",
            content = [Content(schema = Schema(implementation = TestObjectResponse::class))]
        ), ApiResponse(
            description = "I'm a teapot",
            responseCode = "418",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]
        )]
    )
    @PostMapping(
        value = ["/process"],
        consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun processRequest(@RequestBody request: TestObjectRequest): TestObjectResponse {
        logger.info("Request to '/api/v1/operation/process' with params: '{}'", request)
        if (request.id!! < 100) {
            throw TooLowArgumentException(String.format("Id '%d' too low", request.id))
        }
        return TestObjectResponse(CODE, NAME)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(OperationController::class.java)
        private const val CODE = 100
        private const val NAME = "TEST"
    }
}