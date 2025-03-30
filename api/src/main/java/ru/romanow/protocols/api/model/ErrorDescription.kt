package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(localName = "description")
class ErrorDescription {

    @XmlElement
    var field: String? = null

    @XmlElement
    var error: String? = null
}
