package ru.romanow.protocols.graphql.service;

import ru.romanow.protocols.graphql.model.AuthorInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface AuthorService {

    @Nullable
    AuthorInfo getAuthorById(@Nullable Integer id);

    @Nonnull
    List<AuthorInfo> getAuthors();
}
