package ru.romanow.protocols.graphql.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.romanow.protocols.graphql.domain.Author
import ru.romanow.protocols.graphql.model.AuthorResponse
import ru.romanow.protocols.graphql.repository.AuthorRepository

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository
) : AuthorService {

    @Transactional(readOnly = true)
    override fun getAuthorById(id: Int?) =
        if (id != null) {
            authorRepository.findById(id)
                .map { buildAuthorInfo(it) }
                .orElse(null)
        } else null

    @Transactional(readOnly = true)
    override fun authors() =
        authorRepository
            .findAll()
            .map { buildAuthorInfo(it) }

    @Transactional(readOnly = true)
    override fun getAuthorBooksCount(authorId: Int) =
        authorRepository.getAuthorBooksCount(authorId)

    @Transactional
    override fun createAuthor(name: String, age: Int?, experience: Int?): AuthorResponse {
        val author = Author(
            name = name,
            age = age,
            experience = experience
        )
        return buildAuthorInfo(authorRepository.save(author))
    }
}