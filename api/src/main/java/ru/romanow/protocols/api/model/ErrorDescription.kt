package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@NoArgConstructor
@JacksonXmlRootElement(localName = "description")
data class ErrorDescription(
    val field: String,
    val error: String
)