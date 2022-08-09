package ru.romanow.protocols.graphql.queries;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.romanow.protocols.graphql.model.SubscriptionInfo;

import java.time.Duration;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomUtils.nextInt;

@Service
public class PublicationSubscription
        implements GraphQLSubscriptionResolver {

    public Publisher<SubscriptionInfo> newBooks() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(pulse -> new SubscriptionInfo(randomAlphanumeric(10), nextInt(100, 1000)));
    }
}
