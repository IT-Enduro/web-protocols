package ru.romanow.protocols.consumer.service;

import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;

public interface OperationService {
    TestObjectResponse makeOperation(TestObjectRequest request);
}