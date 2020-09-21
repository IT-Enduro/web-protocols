package ru.romanow.protocols.rest.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;
import ru.romanow.protocols.rest.exception.TooLowArgumentException;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@Tag(name = "/api/op")
@RequestMapping("/api/op")
public class OperationController {
    private static final Logger logger = LoggerFactory.getLogger(OperationController.class);

    private static final Integer CODE = 100;
    private static final String NAME = "TEST";

    @Operation(description = "Operation return simple response")
    @ApiResponses({
            @ApiResponse(description = "Success", content = @Content(mediaType = APPLICATION_JSON_UTF8_VALUE, schema = @Schema(implementation = TestObjectResponse.class))),
            @ApiResponse(links = { @Link(operationRef = "server-error"), @Link(operationRef = "teapot-error") })
    })
    @PostMapping(value = "/process",
            consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public TestObjectResponse processRequest(
            @Parameter(name = "Request object", required = true)
            @RequestBody TestObjectRequest request) {
        logger.info("Request to '/api/op/process' with params: '{}'", request);
        if (request.getId() < 100) {
            throw new TooLowArgumentException(String.format("Id '%d' too low", request.getId()));
        }

        return new TestObjectResponse(CODE, NAME);
    }
}
