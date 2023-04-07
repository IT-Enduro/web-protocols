package ru.romanow.protocols.restful.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application.services")
data class ServiceUrlProperties(
    val restfulUrl: String
)