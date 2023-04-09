package ru.romanow.protocols.soap

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import ru.romanow.protocols.soap.config.DatabaseTestConfiguration

@ActiveProfiles("test")
@SpringBootTest
@Import(DatabaseTestConfiguration::class)
class SoapServerApplicationTest {

    @Test
    fun test() {
    }
}