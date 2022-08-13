package ru.romanow.protocols.graphql

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class GraphQLApplication

fun main(args: Array<String>) {
    SpringApplication.run(GraphQLApplication::class.java, *args)
}
