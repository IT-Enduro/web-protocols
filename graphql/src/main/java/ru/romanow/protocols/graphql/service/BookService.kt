package ru.romanow.protocols.graphql.service

import ru.romanow.protocols.graphql.model.BookResponse

interface BookService {
    fun getBookById(id: Int): BookResponse
    fun getBooks(name: String): List<BookResponse>
    val books: List<BookResponse>
}