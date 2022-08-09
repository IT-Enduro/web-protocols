package ru.romanow.protocols.soap

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.romanow.protocols.soap.web.WebServiceClient

@SpringBootTest
@ActiveProfiles("test")
internal class SoapClientApplicationTest {

    @Autowired
    private lateinit var clients: List<WebServiceClient>

    @Test
    fun testApp() {
        Assertions.assertThat(clients).isNotEmpty
    }
}