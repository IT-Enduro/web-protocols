package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlElement

@NoArgConstructor
@JacksonXmlRootElement(localName = "servers")
data class ServersResponse(

    @field:XmlElement
    @JacksonXmlProperty(localName = "server")
    @JacksonXmlElementWrapper(useWrapping = false)
    val servers: List<ServerResponse>
)