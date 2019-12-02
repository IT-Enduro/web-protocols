package ru.romanow.protocols.graphql.service;

import ru.romanow.protocols.graphql.model.AuthorResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface AuthorService {

    @Nullable
    AuthorResponse getAuthorById(@Nullable Integer id);

    @Nonnull
    List<AuthorResponse> getAuthors();

    int getAuthorBooksCount(@Nonnull Integer authorId);

    @Nonnull
    AuthorResponse createAuthor(@Nonnull String name, @Nullable Integer age, @Nullable Integer experience);
}
