package ru.romanow.protocols.graphql.model

data class AuthorResponse(
    var id: Int? = null,
    var name: String? = null,
    var age: Int? = null,
    var experience: Int? = null,
)