package ru.romanow.protocols.soap.web;

import ru.romanow.protocols.soap.generated.model.TestObjectRequest;
import ru.romanow.protocols.soap.generated.model.TestObjectResponse;

/**
 * Created by ronin on 18.09.16
 */
public interface WebServiceClient {
    TestObjectResponse makeRequest(TestObjectRequest request);
}
