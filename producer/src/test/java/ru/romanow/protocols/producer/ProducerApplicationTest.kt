package ru.romanow.protocols.producer

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired
import ru.romanow.protocols.producer.web.PingController

@SpringBootTest
internal class ProducerApplicationTest {

    @Autowired
    private lateinit var pingController: PingController

    @Test
    fun testApp() {
        Assertions.assertThat(pingController).isNotNull
    }
}