package ru.romanow.protocols.graphql.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.protocols.graphql.domain.Book;
import ru.romanow.protocols.graphql.model.BookResponse;
import ru.romanow.protocols.graphql.repository.BookRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class BookServiceImpl
        implements BookService {
    private final BookRepository bookRepository;

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(@Nullable Integer id) {
        if (id != null) {
            return bookRepository.findById(id)
                    .map(BuilderHelper::buildBookInfo)
                    .orElse(null);
        }
        return null;
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getBooks(@Nullable String name) {
        return bookRepository
                .findAll(Example.of(new Book().setName(name)))
                .stream()
                .map(BuilderHelper::buildBookInfo)
                .collect(toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(BuilderHelper::buildBookInfo)
                .collect(toList());
    }
}
