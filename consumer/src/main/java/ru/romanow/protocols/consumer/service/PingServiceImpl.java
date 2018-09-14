package ru.romanow.protocols.consumer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PingServiceImpl
        implements PingService {
    private static final String PING_PATH = "/api/ping";

    private final RestTemplate restTemplate;

    @Value("${consumer.address}")
    private String consumerAddress;

    @Override
    public boolean ping() {
        ResponseEntity<Void> response =
                restTemplate.getForEntity(consumerAddress + PING_PATH, Void.class);

        return response.getStatusCode().is2xxSuccessful();
    }
}
