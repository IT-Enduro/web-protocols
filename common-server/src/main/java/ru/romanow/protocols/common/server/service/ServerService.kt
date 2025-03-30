package ru.romanow.protocols.common.server.service

import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.UpdateServerRequest

interface ServerService {
    fun getById(id: Int): ServerResponse
    fun all(): List<ServerResponse>
    fun findInCity(city: String): List<ServerResponse>
    fun create(request: CreateServerRequest): ServerResponse
    fun delete(id: Int)
    fun update(id: Int, request: UpdateServerRequest): ServerResponse
}
