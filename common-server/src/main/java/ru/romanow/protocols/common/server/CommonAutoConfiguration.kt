package ru.romanow.protocols.common.server

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@AutoConfiguration
@ComponentScan("ru.romanow.protocols.common.server")
@EntityScan("ru.romanow.protocols.common.server.domain")
@EnableJpaRepositories("ru.romanow.protocols.common.server.repository")
class CommonAutoConfiguration
