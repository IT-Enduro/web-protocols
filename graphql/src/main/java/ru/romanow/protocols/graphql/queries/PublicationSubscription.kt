package ru.romanow.protocols.graphql.queries

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
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.RandomUtils
import org.reactivestreams.Publisher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.QueryByExampleExecutor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class PublicationSubscription : GraphQLSubscriptionResolver {
    fun newBooks(): Publisher<SubscriptionInfo> {
        return Flux.interval(Duration.ofSeconds(1))
            .map { pulse: Long? ->
                SubscriptionInfo(
                    RandomStringUtils.randomAlphanumeric(10),
                    RandomUtils.nextInt(100, 1000)
                )
            }
    }
}