package ru.romanow.protocols.rpc.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.soap.model.TestObjectRequest;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

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
        TestObjectRequest request =
                new TestObjectRequest()
                        .setId(RandomUtils.nextInt(0, 10))
                        .setSearchString(RandomStringUtils.randomAlphanumeric(10));

        logger.info("Make request to [processor.processRequest] {}", request);
        Object response = client.execute("processor.processRequest", Collections.singletonList(request));
        logger.info("Method [processor.processRequest] return {}", response);
    }

    @Override
    public void testSum() throws XmlRpcException {
        int a = RandomUtils.nextInt(0, 100);
        int b = RandomUtils.nextInt(0, 100);

        logger.info("Make request to [processor.sum] {} and {}", a, b);
        Object sum = client.execute("processor.sum", Lists.newArrayList(a, b));
        logger.info("Method [processor.sum] return {} + {} = {}", a, b, sum);
    }
}
