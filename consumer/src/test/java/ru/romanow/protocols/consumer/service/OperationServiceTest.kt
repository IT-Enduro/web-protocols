package ru.romanow.protocols.consumer.service

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.test.context.ActiveProfiles
import ru.romanow.protocols.api.model.TestObjectRequest
import ru.romanow.protocols.consumer.exception.RestRequestException

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureStubRunner(
    ids = ["ru.romanow.protocols:producer:+:stubs:8080"],
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
internal class OperationServiceTest {

    @Autowired
    private lateinit var operationService: OperationService

    @Test
    fun testMakeOperation() {
        val request = TestObjectRequest(ID, SEARCH_STRING)
        val response = operationService.makeOperation(request)
        assertThat(response.code).isEqualTo(CODE)
        assertThat(response.data).isEqualTo(DATA)
    }

    @Test
    fun testMakeOperationFail() {
        val request = TestObjectRequest(ID, SEARCH_STRING)
        assertThatThrownBy {
            operationService.makeOperation(request)
        }.isInstanceOf(RestRequestException::class.java)
    }

    companion object {
        private const val ID = 1
        private const val SEARCH_STRING = "test"
        private const val CODE = 100
        private const val DATA = "TEST"
    }
}