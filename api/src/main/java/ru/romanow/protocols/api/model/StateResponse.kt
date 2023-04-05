package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "state")
data class StateResponse(
    val id: Int,
    val city: String,
    val country: String
)