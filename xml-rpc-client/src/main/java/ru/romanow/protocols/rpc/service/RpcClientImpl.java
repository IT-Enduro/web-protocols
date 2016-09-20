package ru.romanow.protocols.rpc.service;

import com.google.common.collect.Lists;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcClientRequestImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ronin on 20.09.16
 */
@Service
public class RpcClientImpl
        implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientImpl.class);

    private static final String address = "http://localhost";
    private static final int port = 8881;
    private static final String endpoint = "/rpc";

    private XmlRpcClient client;
    private XmlRpcClientConfigImpl config;

    @PostConstruct
    public void init() throws MalformedURLException {
        config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(address + ":" + port + endpoint));
        config.setEnabledForExtensions(true);
        config.setContentLengthOptional(false);

        client = new XmlRpcClient();
        client.setConfig(config);
        client.setTransportFactory(
                new XmlRpcCommonsTransportFactory(client));
    }

    @Override
    public void testRequest() throws XmlRpcException {
        XmlRpcRequest request = new XmlRpcClientRequestImpl(
                config, "processor.processRequest", Lists.newArrayList());
        Object response = client.execute(request);
        logger.info("{}", response);
    }
}
