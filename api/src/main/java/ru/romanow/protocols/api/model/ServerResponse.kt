package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(localName = "server")
class ServerResponse {

    @XmlElement
    var id: Int? = null

    @XmlElement
    var purpose: Purpose? = null

    @XmlElement
    var latency: Int? = null

    @XmlElement
    var bandwidth: Int? = null

    @XmlElement
    var state: StateInfo? = null
}
