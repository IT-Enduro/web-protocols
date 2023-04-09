package ru.romanow.protocols.common.client

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import ru.romanow.protocols.common.client.properties.ServiceUrlProperties

@AutoConfiguration
@ComponentScan("ru.romanow.protocols.common.client")
@EnableConfigurationProperties(ServiceUrlProperties::class)
class CommonAutoConfiguration