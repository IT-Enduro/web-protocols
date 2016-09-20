package ru.romanow.protocols.rpc.web;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.romanow.protocols.rpc.service.Handler;
import ru.romanow.protocols.rpc.service.spring.SpringHandlerMapping;
import ru.romanow.protocols.rpc.service.spring.SpringRequestProcessorFactoryFactory;
import ru.romanow.protocols.rpc.service.TestService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ronin on 20.09.16
 */
@Controller
public class XmlRpcController {
    private static final Logger logger = LoggerFactory.getLogger(XmlRpcController.class);
    private static final int MAX_THREADS = 1;

    @Autowired
    private TestService service;

    private XmlRpcServletServer server;

    @PostConstruct
    public void init()
            throws XmlRpcException {
        XmlRpcServerConfigImpl config = new XmlRpcServerConfigImpl();
        config.setEnabledForExtensions(true);
        config.setBasicEncoding(XmlRpcServerConfigImpl.UTF8_ENCODING);

        server = new XmlRpcServletServer();
        server.setConfig(config);
        server.setMaxThreads(MAX_THREADS);
        SpringHandlerMapping handlerMapping = new SpringHandlerMapping();
        handlerMapping.setRequestProcessorFactoryFactory(
                new SpringRequestProcessorFactoryFactory());

        Map<String, Handler> handlers = new HashMap<>();
        handlers.put("processRequest", service);
        handlerMapping.setHandlerMappings(handlers);

        server.setHandlerMapping(handlerMapping);
    }

    @RequestMapping(value="/rpc", method= RequestMethod.POST)
    public void serve(HttpServletRequest request, HttpServletResponse response)
            throws XmlRpcException {
        try {
            server.execute(request, response);
        } catch (Exception e) {
            throw new XmlRpcException(e.getMessage(), e);
        }
    }
}
