package ru.romanow.protocols.rest.web

import io.swagger.v3.oas.annotations.Hidden
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.romanow.protocols.api.model.ErrorResponse
import ru.romanow.protocols.rest.exception.TooLowArgumentException

@Hidden
@RestControllerAdvice
class ExceptionController {
    private val logger = LoggerFactory.getLogger(ExceptionController::class.java)

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler(TooLowArgumentException::class)
    fun exception(exception: TooLowArgumentException): ErrorResponse {
        logger.warn(exception.message)
        return ErrorResponse(exception.message!!)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun exception(exception: RuntimeException): ErrorResponse {
        logger.error("", exception)
        return ErrorResponse(exception.message!!)
    }
}