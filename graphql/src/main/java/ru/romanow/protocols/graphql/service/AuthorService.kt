package ru.romanow.protocols.graphql.service

import ru.romanow.protocols.graphql.model.AuthorResponse

interface AuthorService {
    fun getAuthorById(id: Int?): AuthorResponse?
    fun authors(): List<AuthorResponse>
    fun getAuthorBooksCount(authorId: Int): Int
    fun createAuthor(name: String, age: Int?, experience: Int?): AuthorResponse
}