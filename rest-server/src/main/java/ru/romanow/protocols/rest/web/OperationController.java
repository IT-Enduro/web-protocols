package ru.romanow.protocols.rest.web;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanow.protocols.api.model.ErrorResponse;
import ru.romanow.protocols.rest.exception.TooLowArgumentException;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;

@RestController
@Api(value = "/api/op", description = "Business operation controller")
@RequestMapping("/api/op")
public class OperationController {
    private static final Logger logger = LoggerFactory.getLogger(OperationController.class);

    private static final Integer CODE = 100;
    private static final String NAME = "TEST";

    @ApiOperation(value = "Operation return simple response",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TestObjectResponse.class),
            @ApiResponse(code = 418, message = "I'm a teapot", response = ErrorResponse.class)
    })
    @PostMapping(value = "/process",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TestObjectResponse processRequest(
            @ApiParam(value = "Request object", required = true)
            @RequestBody TestObjectRequest request) {
        logger.info("Request to '/api/op/process' with params: '{}'", request);
        if (request.getId() < 100) {
            throw new TooLowArgumentException(String.format("Id '%d' too low", request.getId()));
        }

        return new TestObjectResponse(CODE, NAME);
    }
}
