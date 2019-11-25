package ru.romanow.protocols.graphql.web;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.graphql.model.BookResponse;
import ru.romanow.protocols.graphql.service.BookService;

import java.util.List;

@Service
@GraphQLApi
@AllArgsConstructor
public class BookGraph {
    private final BookService bookService;

    @GraphQLQuery(name = "books")
    public List<BookResponse> books() {
        return bookService.getBooks();
    }
}
