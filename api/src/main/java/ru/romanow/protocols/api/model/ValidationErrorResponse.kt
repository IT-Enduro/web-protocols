package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlElement

@NoArgConstructor
@JacksonXmlRootElement(localName = "validation")
data class ValidationErrorResponse(
    @field:XmlElement
    val message: String,

    @field:XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    val error: List<ErrorDescription>
)