package ru.romanow.protocols.producer.web

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.romanow.protocols.api.model.ErrorResponse
import java.lang.RuntimeException

@RestControllerAdvice
class ExceptionController {
    private val logger = LoggerFactory.getLogger(ExceptionController::class.java)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun exception(exception: Exception): ErrorResponse {
        logger.error("", exception)
        return ErrorResponse(exception.message!!)
    }
}