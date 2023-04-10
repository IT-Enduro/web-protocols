package ru.romanow.protocols.graphql.config

import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer


@Configuration
class GraphQLConfiguration {

    @Bean
    fun runtimeWiringConfigurer(): RuntimeWiringConfigurer {
        return RuntimeWiringConfigurer { it.scalar(voidScalar()) }
    }

    @Bean
    fun voidScalar(): GraphQLScalarType {
        return GraphQLScalarType.newScalar()
            .name("Void")
            .description("Empty result")
            .coercing(object : Coercing<Unit, Unit> {
                override fun serialize(result: Any) {
                }

                override fun parseValue(input: Any) {
                }

                override fun parseLiteral(input: Any) {
                }
            })
            .build()
    }
}