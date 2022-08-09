package ru.romanow.protocols.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.romanow.protocols.rest.web.PingController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestServerApplicationTest {

    @Autowired
    private PingController pingController;

    @Test
    void testApp() {
        assertThat(pingController).isNotNull();
    }
}