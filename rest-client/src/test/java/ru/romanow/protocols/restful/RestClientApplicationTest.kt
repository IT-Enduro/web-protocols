package ru.romanow.protocols.restful

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.test.context.ActiveProfiles
import ru.romanow.protocols.common.client.service.ServerClient

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureStubRunner(
    ids = ["ru.romanow.protocols:restful:+:stubs"],
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class RestClientApplicationTest {

    @Autowired
    private lateinit var serverClient: ServerClient

    @Test
    fun testCreate() {
        serverClient.create("DATABASE")
    }

    @Test
    fun testFindAll() {
        serverClient.findAll()
    }

    @Test
    fun testGetById() {
        serverClient.getById(1)
    }

    @Test
    fun testFindInCity() {
        serverClient.findInCity("Moscow")
    }

    @Test
    fun testUpdate() {
        serverClient.update(
            id = 1,
            purpose = "DATABASE",
            latency = 10,
            bandwidth = 10000,
            city = "Yerevan",
            country = "Armenia"
        )
    }

    @Test
    fun testDelete() {
        serverClient.delete(1)
    }
}