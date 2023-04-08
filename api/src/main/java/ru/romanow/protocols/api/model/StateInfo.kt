package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.validation.constraints.NotEmpty
import jakarta.xml.bind.annotation.XmlElement

@NoArgConstructor
@JacksonXmlRootElement(localName = "state")
data class StateInfo(
    @field:XmlElement
    val id: Int? = null,

    @field:XmlElement
    @NotEmpty(message = "{field.not.empty}")
    val city: String? = null,

    @field:XmlElement
    @NotEmpty(message = "{field.not.empty}")
    val country: String? = null
)