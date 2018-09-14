package ru.romanow.protocols.producer.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/op")
public class OperationController {
    private static final Logger logger = LoggerFactory.getLogger(OperationController.class);

    private static final Integer CODE = 100;
    private static final String NAME = "TEST";

    @PostMapping(value = "/process",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TestObjectResponse processRequest(
            @RequestBody TestObjectRequest request) {
        logger.info("Request to '/api/op/process' with params: '{}'", request);
        if (request.getId() < 100) {
            throw new IllegalArgumentException(format("Id '%d' too low", request.getId()));
        }

        return new TestObjectResponse(CODE, NAME);
    }
}
