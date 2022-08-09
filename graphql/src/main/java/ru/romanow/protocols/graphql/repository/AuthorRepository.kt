package ru.romanow.protocols.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.romanow.protocols.graphql.domain.Author;

public interface AuthorRepository
        extends JpaRepository<Author, Integer> {

    @Query("select count(b.id) from Book b where b.author.id = :authorId")
    int getAuthorBooksCount(@Param("authorId") Integer authorId);
}
