package ru.romanow.protocols.consumer.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;
import ru.romanow.protocols.consumer.exception.RestRequestException;

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
    public TestObjectResponse makeOperation(TestObjectRequest request) {
        logger.debug("Make request to '{}' with params '{}'", OPERATION_PROCESS_PATH, request);

        try {
            return restTemplate.postForObject(consumerAddress + OPERATION_PROCESS_PATH, request, TestObjectResponse.class);
        } catch (RestClientResponseException exception) {
            throw new RestRequestException(OPERATION_PROCESS_PATH, exception.getRawStatusCode(), exception.getStatusText());
        }
    }
}
