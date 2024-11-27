package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlElement

@NoArgConstructor
@JacksonXmlRootElement(localName = "error")
data class ErrorResponse(
    @field:XmlElement val message: String? = null
)
