package ru.romanow.protocols.grpc.service

import org.springframework.stereotype.Service
import ru.romanow.protocols.common.client.service.ServerClient

@Service
class GrpcServerClient: ServerClient {
    override fun create(purpose: String): String {
        TODO("Not yet implemented")
    }

    override fun findAll(): String {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): String {
        TODO("Not yet implemented")
    }

    override fun findInCity(city: String): String {
        TODO("Not yet implemented")
    }

    override fun update(
        id: Int,
        purpose: String?,
        latency: Int?,
        bandwidth: Int?,
        city: String?,
        country: String?
    ): String {
        TODO("Not yet implemented")
    }
}