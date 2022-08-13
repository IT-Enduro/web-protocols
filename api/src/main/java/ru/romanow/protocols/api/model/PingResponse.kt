package ru.romanow.protocols.api.model

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
data class PingResponse(
    var response: String? = null
)