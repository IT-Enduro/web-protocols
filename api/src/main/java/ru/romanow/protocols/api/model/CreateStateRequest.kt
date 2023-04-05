package ru.romanow.protocols.api.model

import jakarta.validation.constraints.NotEmpty

data class CreateStateRequest(
    @field:NotEmpty(message = "{field.not.empty}")
    val city: String? = null,
    @field:NotEmpty(message = "{field.not.empty}")
    val country: String? = null
)