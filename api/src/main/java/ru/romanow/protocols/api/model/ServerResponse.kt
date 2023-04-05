package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "server")
data class ServerResponse(
    val id: Int,
    val address: String? = null,
    val purpose: Purpose,
    val latency: Int,
    val bandwidth: Int,
    val stateId: Int
)