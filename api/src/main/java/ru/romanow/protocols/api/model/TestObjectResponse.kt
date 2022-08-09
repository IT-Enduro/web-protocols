package ru.romanow.protocols.api.model

import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class TestObjectResponse(
    @XmlElement var code: Int? = null,
    @XmlElement var data: String? = null
) : Serializable