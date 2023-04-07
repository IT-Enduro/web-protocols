package ru.romanow.protocols.restful

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureStubRunner(
    ids = ["ru.romanow.protocols:restful:+:stubs:8080"],
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class RestClientApplicationTest {

    @Test
    fun test() {
    }
}