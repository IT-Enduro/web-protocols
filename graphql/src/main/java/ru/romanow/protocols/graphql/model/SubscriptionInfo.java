package ru.romanow.protocols.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionInfo {
    private String name;
    private Integer price;
}
