package ru.romanow.protocols.graphql.service

import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.romanow.protocols.graphql.domain.Book
import ru.romanow.protocols.graphql.repository.BookRepository

@Service
class BookServiceImpl(
    private val bookRepository: BookRepository
) : BookService {

    @Transactional(readOnly = true)
    override fun books(authorId: Int?) =
        bookRepository
            .findAll(Example.of(Book(authorId = authorId)))
            .map { buildBookInfo(it) }
}