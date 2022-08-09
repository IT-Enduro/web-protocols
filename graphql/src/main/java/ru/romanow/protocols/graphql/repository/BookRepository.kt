package ru.romanow.protocols.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ru.romanow.protocols.graphql.domain.Book;

public interface BookRepository
        extends JpaRepository<Book, Integer>,
                QueryByExampleExecutor<Book> {
}
