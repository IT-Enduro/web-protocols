package ru.romanow.protocols.graphql.queries;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.graphql.model.AuthorResponse;
import ru.romanow.protocols.graphql.service.AuthorService;

@Service
@RequiredArgsConstructor
public class AuthorMutation
        implements GraphQLMutationResolver {
    private final AuthorService authorService;

    public AuthorResponse createAuthor(String name, Integer age, Integer experience) {
        return authorService.createAuthor(name, age, experience);
    }
}
