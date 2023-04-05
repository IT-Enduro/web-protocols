package ru.romanow.protocols.common.domain

import jakarta.persistence.*

@Entity
@Table(name = "states")
data class State(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "city", nullable = false)
    var city: String? = null,

    @Column(name = "country", nullable = false)
    var country: String? = null,

    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
    var servers: List<Server>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as State

        if (city != other.city) return false
        if (country != other.country) return false

        return true
    }

    override fun hashCode(): Int {
        var result = city.hashCode()
        result = 31 * result + country.hashCode()
        return result
    }

    override fun toString(): String {
        return "State(id=$id, city=$city, country=$country)"
    }
}