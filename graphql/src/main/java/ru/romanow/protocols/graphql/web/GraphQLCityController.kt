package ru.romanow.protocols.graphql.web

import org.springframework.graphql.data.method.annotation.SubscriptionMapping
import org.springframework.stereotype.Controller
import org.springframework.util.ResourceUtils
import reactor.core.publisher.Flux
import java.time.Duration

@Controller
class GraphQLCityController {

    private val cities: List<String>
    private var index = 0L

    init {
        val file = ResourceUtils.getFile("classpath:data/cities.txt")
        cities = file.readLines()
    }

    @SubscriptionMapping("cities")
    fun newBooks(): Flux<String> =
        Flux.interval(Duration.ofSeconds(1))
            .map { cities[(index++ % cities.size).toInt()] }
}