package ru.romanow.protocols.graphql.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.protocols.graphql.domain.Book;
import ru.romanow.protocols.graphql.model.BookResponse;
import ru.romanow.protocols.graphql.repository.BookRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class BookServiceImpl
        implements BookService {
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(int id) {
        return bookRepository.findById(id)
                .map(BuilderHelper::buildBookInfo)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getBooks(String name) {
        return bookRepository
                .findAll(Example.of(new Book().setName(name)))
                .stream()
                .map(BuilderHelper::buildBookInfo)
                .collect(toList());
    }

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
