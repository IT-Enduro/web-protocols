package ru.romanow.protocols.graphql.service;

import ru.romanow.protocols.graphql.model.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse getBookById(int id);
    List<BookResponse> getBooks(String name);
    List<BookResponse> getBooks();
}
