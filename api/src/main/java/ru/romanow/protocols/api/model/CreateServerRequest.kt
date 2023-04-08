package ru.romanow.protocols.api.model

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.xml.bind.annotation.XmlElement

@NoArgConstructor
data class CreateServerRequest(
    @field:XmlElement
    @NotEmpty(message = "{field.not.empty}")
    val purpose: String? = null,

    @field:XmlElement
    @Min(value = 0, message = "{field.min}")
    @Max(value = 100, message = "{field.max}")
    @NotNull(message = "{field.not.null}")
    val latency: Int? = null,

    @field:XmlElement
    @Min(value = 0, message = "{field.min}")
    @Max(value = 10000000, message = "{field.max}")
    @NotNull(message = "{field.not.null}")
    val bandwidth: Int? = null,

    @field:XmlElement
    @NotNull(message = "{field.not.null}")
    val state: StateInfo? = null
)