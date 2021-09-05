package ru.romanow.protocols.graphql.service;

import ru.romanow.protocols.graphql.domain.Author;
import ru.romanow.protocols.graphql.domain.Book;
import ru.romanow.protocols.graphql.model.AuthorResponse;
import ru.romanow.protocols.graphql.model.BookResponse;

public class BuilderHelper {
    public static AuthorResponse buildAuthorInfo(Author author) {
        return new AuthorResponse()
                .setId(author.getId())
                .setName(author.getName())
                .setAge(author.getAge())
                .setExperience(author.getExperience());
    }

    public static BookResponse buildBookInfo(Book book) {
        return new BookResponse()
                .setId(book.getId())
                .setIsn(book.getIsn())
                .setName(book.getName())
                .setPrice(book.getPrice())
                .setPageCount(book.getPageCount())
                .setAuthor(buildAuthorInfo(book.getAuthor()));
    }
}
