package ru.romanow.protocols.graphql.model

data class BookResponse(
    var id: Int? = null,
    var name: String? = null,
    var isn: String? = null,
    var price: Int? = null,
    var pageCount: Int? = null,
    var author: AuthorResponse? = null,
)