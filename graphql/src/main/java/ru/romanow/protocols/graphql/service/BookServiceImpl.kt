package ru.romanow.protocols.graphql.service

import lombok.AllArgsConstructor
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.romanow.protocols.graphql.domain.Book
import ru.romanow.protocols.graphql.model.BookResponse
import java.util.function.Function

@Service
@AllArgsConstructor
class BookServiceImpl : BookService {
    private val bookRepository: BookRepository? = null
    @Transactional(readOnly = true)
    override fun getBookById(id: Int): BookResponse {
        return bookRepository.findById(id)
            .map(Function { obj: Book? -> BuilderHelper.buildBookInfo() })
            .orElse(null)
    }

    @Transactional(readOnly = true)
    override fun getBooks(name: String?): List<BookResponse?> {
        return bookRepository
            .findAll(Example.of(Book().setName(name)))
            .stream()
            .map { obj: BuilderHelper?, book: Book -> BuilderHelper.buildBookInfo(book) }
            .collect(Collectors.toList())
    }

    @get:Transactional(readOnly = true)
    override val books: List<BookResponse?>
        get() = bookRepository
            .findAll()
            .stream()
            .map(Function { obj: Book? -> BuilderHelper.buildBookInfo() })
            .collect(Collectors.toList())
}