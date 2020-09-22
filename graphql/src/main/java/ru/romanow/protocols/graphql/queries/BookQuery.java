package ru.romanow.protocols.graphql.queries;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.graphql.model.BookResponse;
import ru.romanow.protocols.graphql.service.BookService;

import java.util.List;

@Service
@AllArgsConstructor
public class BookQuery
        implements GraphQLQueryResolver {
    private final BookService bookService;

    public List<BookResponse> books() {
        return bookService.getBooks();
    }
}
