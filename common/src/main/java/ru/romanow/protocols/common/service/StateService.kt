package ru.romanow.protocols.common.service

import ru.romanow.protocols.api.model.CreateStateRequest
import ru.romanow.protocols.api.model.StateResponse
import ru.romanow.protocols.common.domain.State

interface StateService {
    fun findById(id: Int): State
    fun getById(id: Int): StateResponse
    fun all(): List<StateResponse>
    fun create(request: CreateStateRequest): Int
    fun delete(id: Int)
    fun edit(id: Int, request: CreateStateRequest): StateResponse
}