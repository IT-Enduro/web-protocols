package ru.romanow.protocols.graphql.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class AuthorResponse {
    private Integer id;
    private String name;
    private Integer age;
    private Integer experience;
}
