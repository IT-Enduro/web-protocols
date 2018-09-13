package ru.romanow.protocols.rpc.service;

import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;

/**
 * Created by ronin on 20.09.16
 */
public interface RemoteService {
    TestObjectResponse processRequest(TestObjectRequest request);

    int sum(int a, int b);
}
