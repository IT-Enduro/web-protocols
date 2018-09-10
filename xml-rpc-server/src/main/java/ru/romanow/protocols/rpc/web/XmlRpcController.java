package ru.romanow.protocols.rpc.web;

import lombok.RequiredArgsConstructor;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.romanow.protocols.rpc.service.RemoteService;
import ru.romanow.protocols.rpc.service.spring.SpringHandlerMapping;
import ru.romanow.protocols.rpc.service.spring.SpringRequestProcessorFactoryFactory;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ronin on 20.09.16
 */
@Controller
@RequiredArgsConstructor
public class XmlRpcController {
    private static final int MAX_THREADS = 1;

    private final RemoteService remoteService;
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

        Map<String, RemoteService> handlers = new HashMap<>();
        handlers.put("processor", remoteService);
        handlerMapping.setHandlerMappings(handlers);

        server.setHandlerMapping(handlerMapping);
    }

    @RequestMapping(value = "/")
    public void serve(HttpServletRequest request, HttpServletResponse response)
            throws XmlRpcException {
        try {
            server.execute(request, response);
        } catch (Exception e) {
            throw new XmlRpcException(e.getMessage(), e);
        }
    }
}
