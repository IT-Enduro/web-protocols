package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(localName = "state")
class StateInfo {

    @XmlElement
    var id: Int? = null

    @XmlElement
    var city: String? = null

    @XmlElement
    var country: String? = null
}
