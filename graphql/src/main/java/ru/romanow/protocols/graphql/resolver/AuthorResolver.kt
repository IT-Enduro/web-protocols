package ru.romanow.protocols.graphql.resolver

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class AuthorResolver : GraphQLResolver<AuthorResponse?> {
    private val authorService: AuthorService? = null
    fun booksCount(response: AuthorResponse): Int {
        return authorService.getAuthorBooksCount(response.getId())
    }
}