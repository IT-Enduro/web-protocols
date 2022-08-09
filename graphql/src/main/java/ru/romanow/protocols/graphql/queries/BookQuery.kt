package ru.romanow.protocols.graphql.queries

import lombok.AllArgsConstructor
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class BookQuery : GraphQLQueryResolver {
    private val bookService: BookService? = null
    fun books(): List<BookResponse> {
        return bookService.getBooks()
    }
}