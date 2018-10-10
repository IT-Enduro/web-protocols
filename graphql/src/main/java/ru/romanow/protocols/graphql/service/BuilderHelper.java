package ru.romanow.protocols.graphql.service;

import ru.romanow.protocols.graphql.domain.Author;
import ru.romanow.protocols.graphql.domain.Book;
import ru.romanow.protocols.graphql.model.AuthorInfo;
import ru.romanow.protocols.graphql.model.BookInfo;

import javax.annotation.Nonnull;

public class BuilderHelper {


    public static AuthorInfo buildAuthorInfo(@Nonnull Author author) {
        return new AuthorInfo()
                .setId(author.getId())
                .setName(author.getName())
                .setAge(author.getAge())
                .setExperience(author.getExperience());
    }

    public static BookInfo buildBookInfo(@Nonnull Book book) {
        return new BookInfo()
                .setId(book.getId())
                .setIsn(book.getIsn())
                .setName(book.getName())
                .setPrice(book.getPrice())
                .setPageCount(book.getPageCount())
                .setAuthor(buildAuthorInfo(book.getAuthor()));
    }
}
