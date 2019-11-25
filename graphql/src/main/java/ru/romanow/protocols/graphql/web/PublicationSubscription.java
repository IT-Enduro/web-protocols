package ru.romanow.protocols.graphql.web;

import io.leangen.graphql.annotations.GraphQLSubscription;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.romanow.protocols.graphql.model.SubscriptionInfo;

import java.time.Duration;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomUtils.nextInt;

@Service
@GraphQLApi
public class PublicationSubscription {

    @GraphQLSubscription(name = "newBooks")
    public Publisher<SubscriptionInfo> newBooks() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(pulse -> new SubscriptionInfo(randomAlphanumeric(10), nextInt(100, 1000)));
    }
}
