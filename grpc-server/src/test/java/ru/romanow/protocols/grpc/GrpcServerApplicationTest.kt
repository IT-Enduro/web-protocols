package ru.romanow.protocols.grpc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.romanow.protocols.grpc.web.TestGrpcService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GrpcServerApplicationTest {

    @Autowired
    private TestGrpcService testGrpcService;

    @Test
    void testApp() {
        assertThat(testGrpcService).isNotNull();
    }
}