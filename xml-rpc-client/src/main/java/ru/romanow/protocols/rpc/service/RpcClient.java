package ru.romanow.protocols.rpc.service;

import org.apache.xmlrpc.XmlRpcException;

public interface RpcClient {
    void testRequest() throws XmlRpcException;

    void testSum() throws XmlRpcException;
}
