package ru.romanow.protocols.graphql.web;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import ru.romanow.protocols.graphql.model.AuthorInfo;
import ru.romanow.protocols.graphql.service.AuthorService;
import ru.romanow.protocols.graphql.utils.annotations.GraphQLService;

@GraphQLService
@AllArgsConstructor
public class AuthorGraph {
    private final AuthorService authorService;

    @GraphQLQuery(name = "author")
    public AuthorInfo cart(@GraphQLArgument(name = "id") Integer id) {
        return authorService.getAuthorById(id);
    }
}
