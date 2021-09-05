package ru.romanow.protocols.rest.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanow.protocols.api.model.ErrorResponse;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;
import ru.romanow.protocols.rest.exception.TooLowArgumentException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@Tag(name = "Operation controller")
@RequestMapping("/api/v1/operation")
public class OperationController {
    private static final Logger logger = LoggerFactory.getLogger(OperationController.class);

    private static final Integer CODE = 100;
    private static final String NAME = "TEST";

    @Operation(
            summary = "Operation return simple response",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = TestObjectResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = TestObjectResponse.class))
                            }
                    ),
                    @ApiResponse(
                            description = "I'm a teapot",
                            responseCode = "418",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @PostMapping(value = "/process",
            consumes = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE },
            produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
    public TestObjectResponse processRequest(@RequestBody TestObjectRequest request) {
        logger.info("Request to '/api/v1/operation/process' with params: '{}'", request);
        if (request.getId() < 100) {
            throw new TooLowArgumentException(String.format("Id '%d' too low", request.getId()));
        }

        return new TestObjectResponse(CODE, NAME);
    }
}
