package ru.romanow.protocols.graphql.queries

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class AuthorMutation : GraphQLMutationResolver {
    private val authorService: AuthorService? = null
    fun createAuthor(name: String?, age: Int?, experience: Int?): AuthorResponse {
        return authorService.createAuthor(name, age, experience)
    }
}