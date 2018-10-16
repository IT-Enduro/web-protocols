package ru.romanow.protocols.graphql.model;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class AuthorResponse {

    @GraphQLQuery(name = "id")
    private Integer id;
    @GraphQLQuery(name = "name")
    private String name;
    @GraphQLQuery(name = "age")
    private Integer age;
    @GraphQLQuery(name = "experience")
    private Integer experience;
    @GraphQLQuery(name = "booksCount")
    private Integer booksCount;
}
