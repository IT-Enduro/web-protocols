package ru.romanow.protocols.graphql.service;

import ru.romanow.protocols.graphql.model.BookInfo;

import javax.annotation.Nonnull;
import java.util.List;

public interface BookService {

    @Nonnull
    BookInfo getBookById(@Nonnull Integer id);

    @Nonnull
    List<BookInfo> getBooks(@Nonnull String name);

    @Nonnull
    List<BookInfo> getBooks();
}
