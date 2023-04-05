package ru.romanow.protocols.common.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.StateResponse
import ru.romanow.protocols.api.model.findPurpose
import ru.romanow.protocols.common.domain.Server
import ru.romanow.protocols.common.repository.ServerRepository

@Service
class ServerServiceImpl(
    private val serverRepository: ServerRepository,
    private val stateService: StateService
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
    override fun findByAddress(address: String): List<ServerResponse> =
        serverRepository.findServersByAddress(address)
            .map { buildServerResponse(it) }

    @Transactional
    override fun create(request: CreateServerRequest): Int {
        var server = Server(
            address = request.address,
            latency = request.latency!!,
            bandwidth = request.bandwidth!!,
            purpose = findPurpose(request.purpose!!),
            state = stateService.findById(request.stateId!!)
        )
        server = serverRepository.save(server)
        return server.id!!
    }

    @Transactional
    override fun delete(id: Int) {
        serverRepository.deleteById(id)
    }

    @Transactional
    override fun update(id: Int, request: CreateServerRequest, fullUpdate: Boolean): ServerResponse {
        val server = serverRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Server not found for ID $id") }

        if (request.address != null || fullUpdate) {
            server.address = request.address
        }
        request.bandwidth?.let { server.bandwidth = it }
        request.latency?.let { server.latency = it }
        request.purpose?.let { server.purpose = findPurpose(it) }

        if (request.stateId != null) {
            server.state = stateService.findById(request.stateId!!)
        }
        return buildServerResponse(server)
    }

    @Transactional(readOnly = true)
    override fun serverState(id: Int): StateResponse {
        val server = getById(id)
        return stateService.getById(server.stateId)
    }

    private fun buildServerResponse(server: Server) =
        ServerResponse(
            id = server.id!!,
            address = server.address,
            bandwidth = server.bandwidth!!,
            latency = server.latency!!,
            purpose = server.purpose!!,
            stateId = server.state.id!!
        )
}