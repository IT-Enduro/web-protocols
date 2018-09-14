package ru.romanow.protocols.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;
import ru.romanow.protocols.consumer.service.OperationService;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@SpringBootApplication
public class ConsumerApplication
        implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerApplication.class);

    @Autowired
    private OperationService operationService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        final int id = 100;
        final String searchString = randomAlphabetic(3);
        TestObjectRequest request = new TestObjectRequest(id, searchString);
        TestObjectResponse response = operationService.makeOperation(request);

        logger.info("{}", response);
    }
}
