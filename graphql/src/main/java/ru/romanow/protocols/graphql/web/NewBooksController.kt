package ru.romanow.protocols.graphql.web

import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.springframework.graphql.data.method.annotation.SubscriptionMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import ru.romanow.protocols.graphql.model.NewBookResponse
import java.time.Duration
import kotlin.random.Random.Default.nextInt

@Controller
class NewBooksController {

    @SubscriptionMapping("newBooks")
    fun newBooks(): Flux<NewBookResponse> =
        Flux.interval(Duration.ofSeconds(1))
            .map { NewBookResponse(randomAlphanumeric(10), nextInt(100, 1000)) }
}