package ru.romanow.protocols.grpc.server.web

import io.grpc.Status
import io.grpc.Status.*
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler
import org.slf4j.LoggerFactory

@GrpcAdvice
class GrpcExceptionHandler {
    private val logger = LoggerFactory.getLogger(GrpcExceptionHandler::class.java)

    @GrpcExceptionHandler(EntityNotFoundException::class)
    fun notFound(exception: EntityNotFoundException): Status {
        return NOT_FOUND.withDescription(exception.message)
    }

    @GrpcExceptionHandler(ConstraintViolationException::class)
    fun badRequest(exception: ConstraintViolationException): Status {
        return INVALID_ARGUMENT.withDescription(buildMessage(exception.constraintViolations))
    }

    @GrpcExceptionHandler(RuntimeException::class)
    fun error(exception: Exception): Status {
        logger.error("", exception)
        return INTERNAL.withDescription(exception.message)
    }

    fun buildMessage(violations: Set<ConstraintViolation<*>>) = violations.joinToString { "${it.propertyPath}: ${it.message}" }
}
