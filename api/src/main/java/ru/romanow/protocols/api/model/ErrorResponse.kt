package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@NoArgConstructor
@JacksonXmlRootElement(localName = "error")
data class ErrorResponse(
    val message: String? = null
)