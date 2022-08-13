package ru.romanow.protocols.consumer.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureStubRunner(
    ids = ["ru.romanow.protocols:producer:+:stubs:8080"],
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
internal class PingServiceTest {

    @Autowired
    private lateinit var pingService: PingService

    @Test
    fun testMakeOperation() {
        val available = pingService.ping()
        Assertions.assertThat(available).isTrue
    }
}