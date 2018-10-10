package ru.romanow.protocols.graphql;

import graphql.GraphQL;
import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romanow.protocols.graphql.web.AuthorGraph;

import java.util.Arrays;

@Configuration
public class GraphQLConfiguration {

    @Bean
    @Autowired
    public GraphQLSchema graphQLSchema(AuthorGraph authorGraph) {
        return new GraphQLSchemaGenerator()
                .withResolverBuilders(new AnnotatedResolverBuilder())
                .withOperationsFromSingleton(authorGraph)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
    }

    @Bean
    @Autowired
    public GraphQL graphQL(AuthorGraph authorGraph) {
        return GraphQL.newGraphQL(graphQLSchema(authorGraph))
                .queryExecutionStrategy(new AsyncExecutionStrategy())
                .instrumentation(new ChainedInstrumentation(Arrays.asList(
                        new MaxQueryComplexityInstrumentation(200),
                        new MaxQueryDepthInstrumentation(20)
                )))
                .build();
    }
}
