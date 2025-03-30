package ru.romanow.protocols.common.server.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.romanow.protocols.api.model.*
import ru.romanow.protocols.common.server.domain.Server
import ru.romanow.protocols.common.server.domain.State
import ru.romanow.protocols.common.server.repository.ServerRepository

@Service
class ServerServiceImpl(
    private val serverRepository: ServerRepository
) : ServerService {

    @Transactional(readOnly = true)
    override fun getById(id: Int): ServerResponse =
        serverRepository.findById(id)
            .map { buildServerResponse(it) }
            .orElseThrow { EntityNotFoundException("Server not found for ID $id") }


    @Transactional(readOnly = true)
    override fun all(): List<ServerResponse> =
        serverRepository.findAll()
            .map { buildServerResponse(it) }


    @Transactional(readOnly = true)
    override fun findInCity(city: String): List<ServerResponse> =
        serverRepository.findInCity(city)
            .map { buildServerResponse(it) }

    @Transactional
    override fun create(request: CreateServerRequest): ServerResponse {
        var server = Server(
            latency = request.latency!!,
            bandwidth = request.bandwidth!!,
            purpose = findPurpose(request.purpose!!),
            state = State(city = request.state?.city, country = request.state?.country)
        )
        server = serverRepository.save(server)
        return buildServerResponse(server)
    }

    @Transactional
    override fun delete(id: Int) {
        serverRepository.deleteById(id)
    }

    @Transactional
    override fun update(id: Int, request: UpdateServerRequest): ServerResponse {
        val server = serverRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Server not found for ID $id") }

        request.bandwidth?.let { server.bandwidth = it }
        request.latency?.let { server.latency = it }
        request.purpose?.let { server.purpose = findPurpose(it) }
        request.state?.city?.let { server.state?.city = it }
        request.state?.country?.let { server.state?.country = it }
        return buildServerResponse(server)
    }

    private fun buildServerResponse(server: Server) =
        ServerResponse().apply {
            id = server.id
            bandwidth = server.bandwidth
            latency = server.latency
            purpose = server.purpose
            state = StateInfo().apply {
                id = server.state?.id
                city = server.state?.city
                country = server.state?.country
            }
        }
}
