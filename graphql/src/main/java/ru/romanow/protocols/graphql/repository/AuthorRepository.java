package ru.romanow.protocols.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanow.protocols.graphql.domain.Author;

public interface AuthorRepository
        extends JpaRepository<Author, Integer> {}
