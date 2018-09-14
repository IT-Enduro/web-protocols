package ru.romanow.protocols.consumer.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PingServiceImpl
        implements PingService {
    private static final Logger logger = LoggerFactory.getLogger(PingService.class);
    private static final String PING_PATH = "/api/ping";

    private final RestTemplate restTemplate;

    @Value("${consumer.address}")
    private String consumerAddress;

    @Override
    public boolean ping() {
        try {
            restTemplate.getForObject(consumerAddress + PING_PATH, Void.class);
            return true;
        } catch (RestClientResponseException exception) {
            logger.warn("Request to '{}' finish with error {}:{}", PING_PATH,
                    exception.getRawStatusCode(), exception.getStatusText());
            return false;
        }
    }
}
