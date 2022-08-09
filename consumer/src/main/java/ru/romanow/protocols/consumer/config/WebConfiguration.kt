package ru.romanow.protocols.consumer.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class WebConfiguration {

    @Bean
    fun restTemplate() = RestTemplate()
}