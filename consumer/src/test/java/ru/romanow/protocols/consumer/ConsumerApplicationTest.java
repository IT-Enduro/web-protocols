package ru.romanow.protocols.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.romanow.protocols.consumer.service.PingService;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class ConsumerApplicationTest {

    @Autowired
    private PingService pingService;

    @Test
    void testApp() {
        assertThat(pingService).isNotNull();
    }
}