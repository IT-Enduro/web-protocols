package ru.romanow.protocols.api.model

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
class UpdateServerRequest {

    @XmlElement
    var purpose: String? = null

    @XmlElement
    @Min(value = 0, message = "{field.min}")
    @Max(value = 100, message = "{field.max}")
    var latency: Int? = null

    @XmlElement
    @Min(value = 0, message = "{field.min}")
    @Max(value = 10000000, message = "{field.max}")
    var bandwidth: Int? = null

    @XmlElement
    var state: StateInfo? = null
}
