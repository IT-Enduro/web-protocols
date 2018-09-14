package ru.romanow.protocols.consumer.exception;

import lombok.Getter;

import static java.lang.String.format;

@Getter
public class RestRequestException
        extends RuntimeException {
    private final String url;
    private final Integer status;
    private final String description;

    public RestRequestException(String url, Integer status, String description) {
        super(format("Exception during request to '%s' with status %d and description %s", url, status, description));
        this.url = url;
        this.status = status;
        this.description = description;
    }
}
