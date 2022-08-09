package ru.romanow.protocols.graphql.service

import ru.romanow.protocols.graphql.model.AuthorResponse
import org.springframework.beans.factory.annotation.Autowired
import graphql.schema.GraphQLSchema
import graphql.execution.AsyncExecutionStrategy
import graphql.execution.instrumentation.ChainedInstrumentation
import graphql.analysis.MaxQueryComplexityInstrumentation
import graphql.analysis.MaxQueryDepthInstrumentation
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import ru.romanow.protocols.graphql.domain.Author
import graphql.kickstart.tools.GraphQLQueryResolver
import ru.romanow.protocols.graphql.service.BookService
import ru.romanow.protocols.graphql.model.BookResponse
import ru.romanow.protocols.graphql.service.AuthorService
import java.lang.RuntimeException
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLSubscriptionResolver
import ru.romanow.protocols.graphql.model.SubscriptionInfo
import reactor.core.publisher.Flux
import ru.romanow.protocols.graphql.service.BuilderHelper
import ru.romanow.protocols.graphql.repository.BookRepository
import java.util.stream.Collectors
import ru.romanow.protocols.graphql.repository.AuthorRepository
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.QueryByExampleExecutor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import ru.romanow.protocols.graphql.domain.Book

object BuilderHelper {
    fun buildAuthorInfo(author: Author): AuthorResponse {
        return AuthorResponse()
            .setId(author.getId())
            .setName(author.getName())
            .setAge(author.getAge())
            .setExperience(author.getExperience())
    }

    fun buildBookInfo(book: Book): BookResponse {
        return BookResponse()
            .setId(book.getId())
            .setIsn(book.getIsn())
            .setName(book.getName())
            .setPrice(book.getPrice())
            .setPageCount(book.getPageCount())
            .setAuthor(buildAuthorInfo(book.getAuthor()))
    }
}