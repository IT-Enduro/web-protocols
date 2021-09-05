package ru.romanow.protocols.soap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.romanow.protocols.soap.web.WebServiceClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SoapClientApplicationTest {

    @Autowired
    private List<WebServiceClient> clients;

    @Test
    void testApp() {
        assertThat(clients).isNotEmpty();
    }
}