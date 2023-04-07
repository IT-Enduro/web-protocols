package ru.romanow.protocols.restful.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val mapper = jacksonObjectMapper()

fun prettyPrint(response: String, cls: Class<out Any>): String =
    mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(mapper.readValue(response, cls))

fun prettyPrint(response: Any): String =
    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response)