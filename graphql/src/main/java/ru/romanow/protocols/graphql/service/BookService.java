package ru.romanow.protocols.graphql.service;

import ru.romanow.protocols.graphql.model.BookInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface BookService {

    @Nullable
    BookInfo getBookById(@Nullable Integer id);

    @Nonnull
    List<BookInfo> getBooks(@Nonnull String name);

    @Nonnull
    List<BookInfo> getBooks();
}
