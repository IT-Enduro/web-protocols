package ru.romanow.protocols.consumer.service;

import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;

import javax.annotation.Nonnull;

public interface OperationService {
    TestObjectResponse makeOperation(@Nonnull TestObjectRequest request);
}
