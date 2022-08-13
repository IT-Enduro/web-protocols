package ru.romanow.protocols.graphql.model

data class NewBookResponse(
    var name: String? = null,
    var price: Int? = null,
)