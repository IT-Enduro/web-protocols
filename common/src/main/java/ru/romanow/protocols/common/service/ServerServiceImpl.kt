package ru.romanow.protocols.common.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.StateInfo
import ru.romanow.protocols.api.model.findPurpose
import ru.romanow.protocols.common.domain.Server
import ru.romanow.protocols.common.domain.State
import ru.romanow.protocols.common.repository.ServerRepository

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
    override fun create(request: CreateServerRequest): Int {
        var server = Server(
            latency = request.latency!!,
            bandwidth = request.bandwidth!!,
            purpose = findPurpose(request.purpose!!),
            state = State(city = request.state?.city, country = request.state?.country)
        )
        server = serverRepository.save(server)
        return server.id!!
    }

    @Transactional
    override fun delete(id: Int) {
        serverRepository.deleteById(id)
    }

    @Transactional
    override fun update(id: Int, request: CreateServerRequest): ServerResponse {
        val server = serverRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Server not found for ID $id") }

        request.bandwidth?.let { server.bandwidth = it }
        request.latency?.let { server.latency = it }
        request.purpose?.let { server.purpose = findPurpose(it) }
        request.state?.city.let { server.state?.city = it }
        request.state?.country.let { server.state?.country = it }
        return buildServerResponse(server)
    }

    private fun buildServerResponse(server: Server) =
        ServerResponse(
            id = server.id!!,
            bandwidth = server.bandwidth!!,
            latency = server.latency!!,
            purpose = server.purpose!!,
            state = StateInfo(
                id = server.state?.id,
                city = server.state?.city,
                country = server.state?.country
            )
        )
}