package ru.romanow.protocols.graphql.service;

import ru.romanow.protocols.graphql.model.AuthorResponse;
import ru.romanow.protocols.graphql.model.CreateAuthorRequest;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface AuthorService {

    @Nullable
    AuthorResponse getAuthorById(@Nullable Integer id);

    @Nonnull
    List<AuthorResponse> getAuthors();

    @Nonnull
    AuthorResponse createAuthor(@Nonnull CreateAuthorRequest authorRequest);

    int getAuthorBooksCount(@Nonnull Integer authorId);
}
