package ru.romanow.protocols.graphql.web;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.graphql.model.AuthorResponse;
import ru.romanow.protocols.graphql.model.CreateAuthorRequest;
import ru.romanow.protocols.graphql.service.AuthorService;

import java.util.List;

@Service
@GraphQLApi
@AllArgsConstructor
public class AuthorGraph {
    private final AuthorService authorService;

    @GraphQLQuery(name = "author")
    public AuthorResponse author(@GraphQLArgument(name = "id") Integer id) {
        return authorService.getAuthorById(id);
    }

    @GraphQLQuery(name = "authors")
    public List<AuthorResponse> authors() {
        return authorService.getAuthors();
    }

    @GraphQLQuery(name = "booksCount")
    public Integer booksCount(@GraphQLContext AuthorResponse response) {
        return authorService.getAuthorBooksCount(response.getId());
    }

    @GraphQLMutation(name = "createAuthor")
    public AuthorResponse createAuthor(@GraphQLArgument(name = "author") CreateAuthorRequest createAuthorRequest) {
        return authorService.createAuthor(createAuthorRequest);
    }
}
