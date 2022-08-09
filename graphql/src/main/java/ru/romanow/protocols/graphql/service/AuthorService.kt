package ru.romanow.protocols.graphql.service;

import ru.romanow.protocols.graphql.model.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse getAuthorById(Integer id);
    List<AuthorResponse> getAuthors();
    int getAuthorBooksCount(Integer authorId);
    AuthorResponse createAuthor(String name, Integer age, Integer experience);
}
