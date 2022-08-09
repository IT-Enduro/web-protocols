package ru.romanow.protocols.grpc

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.romanow.protocols.grpc.service.TestGrpcClient

@SpringBootTest
@ActiveProfiles("test")
internal class GrpcClientApplicationTest {

    @Autowired
    private lateinit var testGrpcClient: TestGrpcClient

    @Test
    fun testApp() {
        Assertions.assertThat(testGrpcClient).isNotNull
    }
}