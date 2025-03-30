package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlElement

@JacksonXmlRootElement(localName = "error")
class ErrorResponse(
    @XmlElement var message: String? = null
)
