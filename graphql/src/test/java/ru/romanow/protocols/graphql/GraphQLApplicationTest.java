package ru.romanow.protocols.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.romanow.protocols.graphql.config.DatabaseTestConfiguration;

@SpringBootTest
@Import(DatabaseTestConfiguration.class)
class GraphQLApplicationTest {

    @Test
    void testApp() {

    }
}