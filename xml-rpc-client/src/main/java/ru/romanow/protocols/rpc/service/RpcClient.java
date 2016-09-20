package ru.romanow.protocols.rpc.service;

import org.apache.xmlrpc.XmlRpcException;

/**
 * Created by ronin on 20.09.16
 */
public interface RpcClient {
    void testRequest() throws XmlRpcException;
}
