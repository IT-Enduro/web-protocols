package ru.romanow.protocols.common.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.romanow.protocols.common.domain.Server

interface ServerRepository : JpaRepository<Server, Int> {

    @Query("select s from Server s where s.state.city = :city")
    fun findInCity(@Param("city") city: String): List<Server>
}