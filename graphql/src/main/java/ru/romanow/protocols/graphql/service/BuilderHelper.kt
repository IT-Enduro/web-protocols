package ru.romanow.protocols.graphql.service

import ru.romanow.protocols.graphql.domain.Author
import ru.romanow.protocols.graphql.domain.Book
import ru.romanow.protocols.graphql.model.AuthorResponse
import ru.romanow.protocols.graphql.model.BookResponse

fun buildAuthorInfo(author: Author) =
    AuthorResponse(
        id = author.id,
        name = author.name,
        age = author.age,
        experience = author.experience
    )

fun buildBookInfo(book: Book) =
    BookResponse(
        id = book.id,
        isn = book.isn,
        name = book.name,
        price = book.price,
        pageCount = book.pageCount,
        author = buildAuthorInfo(book.author!!)
    )