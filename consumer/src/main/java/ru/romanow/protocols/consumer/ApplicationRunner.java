package ru.romanow.protocols.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;
import ru.romanow.protocols.consumer.service.OperationService;
import ru.romanow.protocols.consumer.service.PingService;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Profile("!test")
@Configuration
public class ApplicationRunner
        implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerApplication.class);

    @Autowired
    private OperationService operationService;

    @Autowired
    private PingService pingService;

    @Override
    public void run(String... args) {
        if (pingService.ping()) {
            final int id = 100;
            final String searchString = randomAlphabetic(3);
            TestObjectRequest request = new TestObjectRequest(id, searchString);
            TestObjectResponse response = operationService.makeOperation(request);

            logger.info("{}", response);
        } else {
            logger.warn("Service unavailable");
        }
    }
}
