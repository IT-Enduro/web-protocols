package ru.romanow.protocols.common.server.domain

import jakarta.persistence.*
import jakarta.persistence.CascadeType.MERGE
import jakarta.persistence.CascadeType.PERSIST
import jakarta.persistence.CascadeType.REFRESH
import ru.romanow.protocols.api.model.Purpose

@Entity
@Table(
    name = "servers",
    indexes = [Index(name = "idx_servers_state_id", columnList = "state_id")]
)
data class Server(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "purpose", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    var purpose: Purpose? = null,

    @Column(name = "latency", nullable = false)
    var latency: Int? = null,

    @Column(name = "bandwidth", nullable = false)
    var bandwidth: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [MERGE, PERSIST, REFRESH])
    @JoinColumn(name = "state_id", foreignKey = ForeignKey(name = "fk_servers_state"))
    var state: State? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Server

        if (id != other.id) return false
        if (purpose != other.purpose) return false
        if (latency != other.latency) return false
        if (bandwidth != other.bandwidth) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (purpose?.hashCode() ?: 0)
        result = 31 * result + (latency ?: 0)
        result = 31 * result + (bandwidth ?: 0)
        return result
    }

    override fun toString(): String {
        return "Server(id=$id, purpose=$purpose, latency=$latency, bandwidth=$bandwidth)"
    }
}