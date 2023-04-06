package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.validation.constraints.NotEmpty

@NoArgConstructor
@JacksonXmlRootElement(localName = "state")
data class StateInfo(
    val id: Int? = null,

    @field:NotEmpty(message = "{field.not.empty}")
    val city: String? = null,

    @field:NotEmpty(message = "{field.not.empty}")
    val country: String? = null
)