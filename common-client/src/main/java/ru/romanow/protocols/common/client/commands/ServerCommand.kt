package ru.romanow.protocols.common.client.commands

import org.slf4j.LoggerFactory
import org.springframework.shell.CompletionContext
import org.springframework.shell.CompletionProposal
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import org.springframework.shell.standard.ValueProvider
import org.springframework.stereotype.Component
import ru.romanow.protocols.common.client.service.ServerClient

@ShellComponent
class ServerCommand(
    private val serverClient: ServerClient
) {
    private val logger = LoggerFactory.getLogger(ServerCommand::class.java)

    @ShellMethod(key = ["create"], value = "Create server", prefix = "-")
    fun create(@ShellOption(valueProvider = ServerTypeProvider::class) purpose: String) {
        val response = serverClient.create(purpose)
        logger.info("Created server {}", response)
    }

    @ShellMethod(key = ["find-all"], value = "Get all servers")
    fun findAll() {
        val response = serverClient.findAll()
        logger.info("Get servers:\n{}", response)
    }

    @ShellMethod(key = ["find-by-id"], value = "Get server by Id")
    fun getById(@ShellOption id: Int) {
        val response = serverClient.getById(id)
        logger.info("Find server:\n{}", response)
    }

    @ShellMethod(key = ["find-in-city"], value = "Get servers in city")
    fun findInCity(@ShellOption city: String) {
        val response = serverClient.findInCity(city)
        logger.info("Find servers:\n{}", response)
    }

    @ShellMethod(key = ["update"], value = "Update server by Id")
    fun update(
        @ShellOption id: Int,
        @ShellOption(valueProvider = ServerTypeProvider::class, defaultValue = "empty") purpose: String?,
        @ShellOption(defaultValue = "") latency: Int?,
        @ShellOption(defaultValue = "") bandwidth: Int?,
        @ShellOption(defaultValue = "empty") city: String?,
        @ShellOption(defaultValue = "empty") country: String?,
    ) {
        val response = serverClient.update(
            id = id,
            purpose = if (!purpose.equals("empty")) purpose else null,
            latency = latency,
            bandwidth = bandwidth,
            city = if (!city.equals("empty")) city else null,
            country = if (!country.equals("empty")) country else null
        )
        logger.info("Updated server:\n{}", response)
    }
}

@Component
class ServerTypeProvider : ValueProvider {
    override fun complete(completionContext: CompletionContext): List<CompletionProposal> {
        return listOf("DATABASE", "BACKEND", "FRONTEND").map { CompletionProposal(it) }
    }
}