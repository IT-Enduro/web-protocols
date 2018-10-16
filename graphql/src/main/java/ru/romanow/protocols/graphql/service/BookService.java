package ru.romanow.protocols.graphql.service;

import ru.romanow.protocols.graphql.model.BookResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface BookService {

    @Nullable
    BookResponse getBookById(@Nullable Integer id);

    @Nonnull
    List<BookResponse> getBooks(@Nonnull String name);

    @Nonnull
    List<BookResponse> getBooks();
}
