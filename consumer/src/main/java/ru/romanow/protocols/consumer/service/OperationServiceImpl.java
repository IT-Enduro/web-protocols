package ru.romanow.protocols.consumer.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;
import ru.romanow.protocols.consumer.RestRequestException;

import javax.annotation.Nonnull;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl
        implements OperationService {
    private static final Logger logger = LoggerFactory.getLogger(OperationService.class);
    private static final String OPERATION_PROCESS_PATH = "/api/op/process";

    private final RestTemplate restTemplate;

    @Value("${consumer.address}")
    private String consumerAddress;

    @Override
    public TestObjectResponse makeOperation(@Nonnull TestObjectRequest request) {
        logger.debug("Make request to '{}' with params '{}'", OPERATION_PROCESS_PATH, request);

        ResponseEntity<TestObjectResponse> response =
                restTemplate.postForEntity(consumerAddress + OPERATION_PROCESS_PATH, request, TestObjectResponse.class);

        HttpStatus status = response.getStatusCode();
        if (status.is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RestRequestException(OPERATION_PROCESS_PATH, status.value(), status.getReasonPhrase());
        }
    }
}
