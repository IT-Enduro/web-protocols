package ru.romanow.protocols.common.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.romanow.protocols.common.domain.State

interface StateRepository : JpaRepository<State, Int> {
    fun findByCountryAndCity(country: String, city: String): State
}