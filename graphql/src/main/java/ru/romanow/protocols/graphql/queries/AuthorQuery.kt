package ru.romanow.protocols.graphql.queries

import lombok.AllArgsConstructor
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class AuthorQuery : GraphQLQueryResolver {
    private val authorService: AuthorService? = null
    fun author(id: Int?): AuthorResponse {
        return authorService.getAuthorById(id)
    }

    fun authors(): List<AuthorResponse> {
        return authorService.getAuthors()
    }

    fun exception(): Int {
        throw RuntimeException("test")
    }
}