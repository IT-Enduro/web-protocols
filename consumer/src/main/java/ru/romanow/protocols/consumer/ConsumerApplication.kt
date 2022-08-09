package ru.romanow.protocols.consumer;

import org.slf4j.Logger;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;
import ru.romanow.protocols.consumer.service.OperationService;
import ru.romanow.protocols.consumer.service.PingService;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
public class ConsumerApplication {
    private static final Logger logger = getLogger(ConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public ApplicationRunner runner(PingService pingService, OperationService operationService) {
        return args -> {
            if (pingService.ping()) {
                final int id = 100;
                final String searchString = randomAlphabetic(3);
                TestObjectRequest request = new TestObjectRequest(id, searchString);
                TestObjectResponse response = operationService.makeOperation(request);

                logger.info("{}", response);
            } else {
                logger.warn("Service unavailable");
            }
        };
    }
}
