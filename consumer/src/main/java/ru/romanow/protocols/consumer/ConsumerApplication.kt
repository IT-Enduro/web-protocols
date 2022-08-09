package ru.romanow.protocols.consumer

import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import ru.romanow.protocols.api.model.TestObjectRequest
import ru.romanow.protocols.consumer.service.OperationService
import ru.romanow.protocols.consumer.service.PingService


@SpringBootApplication
class ConsumerApplication {
    private val logger = LoggerFactory.getLogger(ConsumerApplication::class.java)

    @Bean
    @Profile("!test")
    fun runner(pingService: PingService, operationService: OperationService): ApplicationRunner {
        return ApplicationRunner {
            if (pingService.ping()) {
                val id = 100
                val searchString = RandomStringUtils.randomAlphabetic(3)
                val request = TestObjectRequest(id, searchString)
                val response = operationService.makeOperation(request)
                logger.info("{}", response)
            } else {
                logger.warn("Service unavailable")
            }
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ConsumerApplication::class.java, *args)
}