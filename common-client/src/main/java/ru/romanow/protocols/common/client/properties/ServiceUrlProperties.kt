package ru.romanow.protocols.common.client.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application.services")
data class ServiceUrlProperties(
    val serverUrl: String
)