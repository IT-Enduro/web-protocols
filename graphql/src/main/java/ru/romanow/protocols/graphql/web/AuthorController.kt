package ru.romanow.protocols.graphql.web

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import ru.romanow.protocols.graphql.model.AuthorResponse
import ru.romanow.protocols.graphql.service.AuthorService

@Controller
class AuthorController(
    private val authorService: AuthorService
) {
    @QueryMapping("author")
    fun author(@Argument("id") id: Int?) = authorService.getAuthorById(id)

    @QueryMapping("authors")
    fun authors() = authorService.authors()

    @SchemaMapping(typeName = "AuthorResponse", field = "booksCount")
    fun booksCount(author: AuthorResponse) =
        authorService.getAuthorBooksCount(author.id!!)

    @MutationMapping("createAuthor")
    fun createAuthor(
        @Argument("name") name: String,
        @Argument("age") age: Int?,
        @Argument("experience") experience: Int?
    ) = authorService.createAuthor(name, age, experience)
}