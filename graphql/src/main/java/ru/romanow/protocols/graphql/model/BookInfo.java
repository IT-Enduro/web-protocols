package ru.romanow.protocols.graphql.model;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookInfo {

    @GraphQLQuery(name = "id")
    private Integer id;
    @GraphQLQuery(name = "name")
    private String name;
    @GraphQLQuery(name = "isn")
    private String isn;
    @GraphQLQuery(name = "price")
    private Integer price;
    @GraphQLQuery(name = "pageCount")
    private Integer pageCount;
    @GraphQLQuery(name = "author")
    private AuthorInfo author;
}
