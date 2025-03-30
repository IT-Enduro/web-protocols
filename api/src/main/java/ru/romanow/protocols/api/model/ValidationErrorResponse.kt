package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(localName = "validation")
class ValidationErrorResponse(

    @XmlElement
    val message: String,

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    val error: List<ErrorDescription>
)
