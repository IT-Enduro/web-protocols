package ru.romanow.protocols.api.model

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

@NoArgConstructor
data class CreateServerRequest(
    @field:NotEmpty(message = "{field.not.empty}")
    val purpose: String? = null,

    @field:Min(value = 0, message = "{field.min}")
    @field:Max(value = 100, message = "{field.max}")
    @field:NotNull(message = "{field.not.null}")
    val latency: Int? = null,

    @field:Min(value = 0, message = "{field.min}")
    @field:Max(value = 10000000, message = "{field.max}")
    @field:NotNull(message = "{field.not.null}")
    val bandwidth: Int? = null,

    @field:NotNull(message = "{field.not.null}")
    val state: StateInfo? = null
)