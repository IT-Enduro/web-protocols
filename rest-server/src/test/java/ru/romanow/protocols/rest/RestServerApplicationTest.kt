package ru.romanow.protocols.rest

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.romanow.protocols.rest.web.PingController

@SpringBootTest
internal class RestServerApplicationTest {

    @Autowired
    private lateinit var pingController: PingController

    @Test
    fun testApp() {
        Assertions.assertThat(pingController).isNotNull
    }
}