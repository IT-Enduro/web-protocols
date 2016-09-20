package ru.romanow.protocols.rpc;

import ru.romanow.protocols.soap.model.TestObjectRequest;
import ru.romanow.protocols.soap.model.TestObjectResponse;

/**
 * Created by ronin on 18.09.16
 */
public interface RpcWebService {
    TestObjectResponse processRequest(TestObjectRequest request);
}
