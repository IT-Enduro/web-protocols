package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlElement

@NoArgConstructor
@JacksonXmlRootElement(localName = "description")
data class ErrorDescription(
    @field:XmlElement val field: String,
    @field:XmlElement val error: String
)
