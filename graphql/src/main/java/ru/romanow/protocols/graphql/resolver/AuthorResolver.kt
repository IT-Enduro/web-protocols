package ru.romanow.protocols.graphql.resolver;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romanow.protocols.graphql.model.AuthorResponse;
import ru.romanow.protocols.graphql.service.AuthorService;

@Component
@RequiredArgsConstructor
public class AuthorResolver
        implements GraphQLResolver<AuthorResponse> {
    private final AuthorService authorService;

    public Integer booksCount(AuthorResponse response) {
        return authorService.getAuthorBooksCount(response.getId());
    }
}
