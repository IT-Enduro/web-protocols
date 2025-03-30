package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(localName = "servers")
class ServersResponse(

    @XmlElement
    @JacksonXmlProperty(localName = "server")
    @JacksonXmlElementWrapper(useWrapping = false)
    val servers: List<ServerResponse>
)
