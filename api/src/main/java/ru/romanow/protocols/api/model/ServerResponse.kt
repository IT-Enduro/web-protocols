package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@NoArgConstructor
@JacksonXmlRootElement(localName = "server")
data class ServerResponse(
    val id: Int,
    val purpose: Purpose,
    val latency: Int,
    val bandwidth: Int,
    val state: StateInfo
)