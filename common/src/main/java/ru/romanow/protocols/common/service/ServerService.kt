package ru.romanow.protocols.common.service

import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.StateResponse

interface ServerService {
    fun getById(id: Int): ServerResponse
    fun all(): List<ServerResponse>
    fun findByAddress(address: String): List<ServerResponse>
    fun create(request: CreateServerRequest): Int
    fun delete(id: Int)
    fun update(id: Int, request: CreateServerRequest, fullUpdate: Boolean = false): ServerResponse
    fun serverState(id: Int): StateResponse
}