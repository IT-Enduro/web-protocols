package ru.romanow.protocols.graphql.config

import graphql.GraphQL
import graphql.analysis.MaxQueryComplexityInstrumentation
import graphql.analysis.MaxQueryDepthInstrumentation
import graphql.execution.AsyncExecutionStrategy
import graphql.execution.instrumentation.ChainedInstrumentation
import graphql.schema.GraphQLSchema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraphQLConfiguration {

    @Bean
    @Autowired
    fun graphQL(graphQLSchema: GraphQLSchema?): GraphQL {
        return GraphQL.newGraphQL(graphQLSchema)
            .queryExecutionStrategy(AsyncExecutionStrategy())
            .instrumentation(
                ChainedInstrumentation(
                    listOf(
                        MaxQueryComplexityInstrumentation(200),
                        MaxQueryDepthInstrumentation(20)
                    )
                )
            )
            .build()
    }
}