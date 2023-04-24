package ru.romanow.protocols.restful.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "server.https")
data class HttpsProperties(
    val enabled: Boolean = false,
    val port: Int? = null,
    val keyStore: String? = null,
    val keyStorePassword: String? = null,
    val keyPassword: String? = null,
    val ciphers: String? = null
)
