package ru.romanow.protocols.grpc

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired
import ru.romanow.protocols.grpc.web.TestGrpcService

@SpringBootTest
internal class GrpcServerApplicationTest {

    @Autowired
    private lateinit var testGrpcService: TestGrpcService

    @Test
    fun testApp() {
        Assertions.assertThat(testGrpcService).isNotNull
    }
}