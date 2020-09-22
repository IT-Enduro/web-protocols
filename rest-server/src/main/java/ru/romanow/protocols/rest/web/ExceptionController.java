package ru.romanow.protocols.rest.web;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.romanow.protocols.rest.exception.TooLowArgumentException;
import ru.romanow.protocols.api.model.ErrorResponse;

@RestControllerAdvice
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ApiResponse(responseCode = "418", description = "Fatal Error")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler(TooLowArgumentException.class)
    public ErrorResponse exception(TooLowArgumentException exception) {
        logger.warn(exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }

    @ApiResponse(responseCode = "500", description = "Server Error")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse exception(Exception exception) {
        logger.error("", exception);
        return new ErrorResponse(exception.getMessage());
    }
}
