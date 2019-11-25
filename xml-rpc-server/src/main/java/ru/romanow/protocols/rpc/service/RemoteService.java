package ru.romanow.protocols.rpc.service;

import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;

public interface RemoteService {
    TestObjectResponse processRequest(TestObjectRequest request);

    int sum(int a, int b);
}
