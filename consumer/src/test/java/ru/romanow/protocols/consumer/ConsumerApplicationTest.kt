package ru.romanow.protocols.consumer

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.romanow.protocols.consumer.service.PingService

@ActiveProfiles("test")
@SpringBootTest
internal class ConsumerApplicationTest {

    @Autowired
    private lateinit var pingService: PingService

    @Test
    fun testApp() {
        Assertions.assertThat(pingService).isNotNull
    }
}