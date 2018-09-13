package ru.romanow.protocols.rest.exception;

public class TooLowArgumentException
        extends RuntimeException {
    public TooLowArgumentException(String message) {
        super(message);
    }
}
