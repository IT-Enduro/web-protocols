package ru.romanow.protocols.graphql.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.protocols.graphql.domain.Book;
import ru.romanow.protocols.graphql.model.BookInfo;
import ru.romanow.protocols.graphql.repository.BookRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class BookServiceImpl
        implements BookService {
    private final BookRepository bookRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public BookInfo getBookById(@Nonnull Integer id) {
        return bookRepository
                .findById(id)
                .map(BuilderHelper::buildBookInfo)
                .orElseThrow(() -> new RuntimeException(format("Entity %s for id '%s' not found", "Book", id)));
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<BookInfo> getBooks(@Nullable String name) {
        return bookRepository
                .findAll(Example.of(new Book().setName(name)))
                .stream()
                .map(BuilderHelper::buildBookInfo)
                .collect(toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<BookInfo> getBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(BuilderHelper::buildBookInfo)
                .collect(toList());
    }
}
