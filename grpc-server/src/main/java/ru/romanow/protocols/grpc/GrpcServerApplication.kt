package ru.romanow.protocols.grpc

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class GrpcServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(GrpcServerApplication::class.java, *args)
}
