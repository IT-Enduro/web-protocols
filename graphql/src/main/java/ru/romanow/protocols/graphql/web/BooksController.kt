package ru.romanow.protocols.graphql.web

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import ru.romanow.protocols.graphql.service.BookService


@Controller
class BooksController(
    private val bookService: BookService
) {

    @QueryMapping("books")
    fun books(@Argument("authorId") authorId: Int?) = bookService.books(authorId)
}