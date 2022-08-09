package ru.romanow.protocols.consumer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;
import ru.romanow.protocols.consumer.exception.RestRequestException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureStubRunner(
        ids = "ru.romanow.protocols:producer:+:stubs:8080",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
public class OperationServiceTest {
    private static final Integer CODE = 100;
    private static final String DATA = "TEST";

    @Autowired
    private OperationService operationService;

    @Test
    public void testMakeOperation() {
        final int id = 101;
        final String searchString = "test";
        TestObjectRequest request = new TestObjectRequest(id, searchString);
        TestObjectResponse response = operationService.makeOperation(request);

        assertThat(response.getCode()).isEqualTo(CODE);
        assertThat(response.getData()).isEqualTo(DATA);
    }

    @Test
    public void testMakeOperationFail() {
        final int id = 1;
        final String searchString = "test";
        TestObjectRequest request = new TestObjectRequest(id, searchString);
        assertThatThrownBy(() -> operationService.makeOperation(request)).isInstanceOf(RestRequestException.class);
    }
}
