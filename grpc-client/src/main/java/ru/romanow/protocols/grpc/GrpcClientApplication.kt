package ru.romanow.protocols.grpc

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class GrpcClientApplication

fun main(args: Array<String>) {
    SpringApplication.run(GrpcClientApplication::class.java, *args)
}