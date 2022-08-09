package ru.romanow.protocols.graphql

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import ru.romanow.protocols.graphql.config.DatabaseTestConfiguration

@Disabled
@SpringBootTest
@Import(DatabaseTestConfiguration::class)
internal class GraphQLApplicationTest {

    @Test
    fun testApp() {
    }
}