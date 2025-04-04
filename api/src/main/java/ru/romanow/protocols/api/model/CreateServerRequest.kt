package ru.romanow.protocols.api.model

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
class CreateServerRequest {

    @XmlElement
    @NotEmpty(message = "{field.not.empty}")
    var purpose: String? = null

    @XmlElement
    @Min(value = 0, message = "{field.min}")
    @Max(value = 100, message = "{field.max}")
    @NotNull(message = "{field.not.null}")
    var latency: Int? = null

    @XmlElement
    @Min(value = 0, message = "{field.min}")
    @Max(value = 10000000, message = "{field.max}")
    @NotNull(message = "{field.not.null}")
    var bandwidth: Int? = null

    @XmlElement
    @NotNull(message = "{field.not.null}")
    var state: StateInfo? = null
}
