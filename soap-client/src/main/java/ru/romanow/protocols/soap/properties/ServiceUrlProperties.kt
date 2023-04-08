package ru.romanow.protocols.soap.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application.services")
data class ServiceUrlProperties(
    val soapServerUrl: String
)