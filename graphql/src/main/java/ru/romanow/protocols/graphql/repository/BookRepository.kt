package ru.romanow.protocols.graphql.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.QueryByExampleExecutor
import ru.romanow.protocols.graphql.domain.Book

interface BookRepository : JpaRepository<Book, Int>, QueryByExampleExecutor<Book>