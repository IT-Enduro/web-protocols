package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlElement

@NoArgConstructor
@JacksonXmlRootElement(localName = "server")
data class ServerResponse(
    @field:XmlElement val id: Int,
    @field:XmlElement val purpose: Purpose,
    @field:XmlElement val latency: Int,
    @field:XmlElement val bandwidth: Int,
    @field:XmlElement val state: StateInfo
)
