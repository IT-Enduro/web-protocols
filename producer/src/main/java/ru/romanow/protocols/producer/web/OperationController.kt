package ru.romanow.protocols.producer.web

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.romanow.protocols.api.model.TestObjectRequest
import ru.romanow.protocols.api.model.TestObjectResponse

@RestController
@RequestMapping("/api/v1/operation")
class OperationController {
    private val logger = LoggerFactory.getLogger(OperationController::class.java)

    @PostMapping(
        value = ["/process"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun processRequest(@RequestBody request: TestObjectRequest): TestObjectResponse {
        logger.info("Request to '/api/v1/operation/process' with params: '{}'", request)
        if (request.id!! < 100) {
            throw IllegalArgumentException("Id '${request.id}' too low")
        }
        return TestObjectResponse(CODE, NAME)
    }

    companion object {
        private const val CODE = 100
        private const val NAME = "TEST"
    }
}