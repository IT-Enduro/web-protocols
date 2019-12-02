package ru.romanow.protocols.graphql.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookResponse {
    private Integer id;
    private String name;
    private String isn;
    private Integer price;
    private Integer pageCount;
    private AuthorResponse author;
}
