package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@NoArgConstructor
@JacksonXmlRootElement(localName = "validation")
data class ValidationErrorResponse(
    val message: String,
    @JacksonXmlElementWrapper(useWrapping = false)
    val error: List<ErrorDescription>
)