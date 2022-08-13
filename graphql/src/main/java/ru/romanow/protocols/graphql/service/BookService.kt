package ru.romanow.protocols.graphql.service

import ru.romanow.protocols.graphql.model.BookResponse

interface BookService {
    fun books(authorId: Int?): List<BookResponse>
}