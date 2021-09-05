package ru.romanow.protocols.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.romanow.protocols.producer.web.PingController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProducerApplicationTest {

    @Autowired
    private PingController pingController;

    @Test
    void testApp() {
        assertThat(pingController).isNotNull();
    }
}