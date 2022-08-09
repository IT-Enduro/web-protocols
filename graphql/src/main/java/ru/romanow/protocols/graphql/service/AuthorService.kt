package ru.romanow.protocols.graphql.service

import ru.romanow.protocols.graphql.model.AuthorResponse

interface AuthorService {
    fun getAuthorById(id: Int): AuthorResponse
    val authors: List<AuthorResponse>
    fun getAuthorBooksCount(authorId: Int): Int
    fun createAuthor(name: String, age: Int, experience: Int): AuthorResponse
}