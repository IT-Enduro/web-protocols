package ru.romanow.protocols.restful

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.test.context.ActiveProfiles
import ru.romanow.protocols.restful.commands.ServerCommand

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureStubRunner(
    ids = ["ru.romanow.protocols:restful:+:stubs"],
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class RestClientApplicationTest {

    @Autowired
    private lateinit var command: ServerCommand

    @Test
    fun testCreate() {
        command.create("DATABASE")
    }

    @Test
    fun testFindAll() {
        command.findAll()
    }

    @Test
    fun testGetById() {
        command.getById(1)
    }

    @Test
    fun testFindInCity() {
        command.findInCity("Moscow")
    }

    @Test
    fun testUpdate() {
        command.update(
            id = 1,
            purpose = "DATABASE",
            latency = 10,
            bandwidth = 10000,
            city = "Yerevan",
            country = "Armenia"
        )
    }
}