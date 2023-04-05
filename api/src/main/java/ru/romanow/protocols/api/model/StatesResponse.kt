package ru.romanow.protocols.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "states")
data class StatesResponse(

    @JacksonXmlProperty(localName = "state")
    @JacksonXmlElementWrapper(useWrapping = false)
    val states: List<StateResponse>
)