package ru.romanow.protocols.grpc

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import ru.romanow.protocols.grpc.service.TestGrpcClient

@SpringBootApplication
class GrpcClientApplication {
    private val logger = LoggerFactory.getLogger(GrpcClientApplication::class.java)

    @Bean
    @Profile("!test")
    fun runner(testGrpcClient: TestGrpcClient): ApplicationRunner {
        return ApplicationRunner {
            logger.info("{}", testGrpcClient.testClient())
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(GrpcClientApplication::class.java, *args)
}