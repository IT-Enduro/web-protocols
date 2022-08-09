package ru.romanow.protocols.grpc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.romanow.protocols.grpc.service.TestGrpcClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class GrpcClientApplicationTest {

    @Autowired
    private TestGrpcClient testGrpcClient;

    @Test
    void testApp() {
        assertThat(testGrpcClient).isNotNull();
    }
}