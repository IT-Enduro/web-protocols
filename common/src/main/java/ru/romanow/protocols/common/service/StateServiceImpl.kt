package ru.romanow.protocols.common.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.romanow.protocols.api.model.CreateStateRequest
import ru.romanow.protocols.api.model.StateResponse
import ru.romanow.protocols.common.domain.State
import ru.romanow.protocols.common.repository.StateRepository

@Service
class StateServiceImpl(
    private val stateRepository: StateRepository
) : StateService {

    @Transactional(readOnly = true)
    override fun findById(id: Int): State =
        stateRepository.findById(id)
            .orElse(null)

    @Transactional(readOnly = true)
    override fun getById(id: Int): StateResponse =
        stateRepository.findById(id)
            .map { buildStateResponse(it) }
            .orElseThrow { EntityNotFoundException("State not found for ID $id") }

    @Transactional(readOnly = true)
    override fun all(): List<StateResponse> =
        stateRepository.findAll()
            .map { buildStateResponse(it) }

    @Transactional
    override fun create(request: CreateStateRequest): Int {
        var state = State(
            city = request.city!!,
            country = request.country!!
        )
        state = stateRepository.save(state)
        return state.id!!
    }

    @Transactional
    override fun delete(id: Int) {
        stateRepository.deleteById(id)
    }

    @Transactional
    override fun edit(id: Int, request: CreateStateRequest): StateResponse {
        val state = stateRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException("State not found for ID $id") }

        request.city?.let { state.city = it }
        request.country?.let { state.country = it }
        return buildStateResponse(state)
    }

    private fun buildStateResponse(state: State) =
        StateResponse(
            id = state.id!!,
            city = state.city!!,
            country = state.country!!
        )
}