package ru.romanow.protocols.rpc.service.spring;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory.StatelessProcessorFactoryFactory;
import ru.romanow.protocols.rpc.service.RemoteService;

import java.util.HashMap;
import java.util.Map;

public class SpringRequestProcessorFactoryFactory
        extends StatelessProcessorFactoryFactory
        implements RequestProcessorFactoryFactory {

    private Map<Class<? extends RemoteService>, RemoteService> classHandlerMappings;

    protected void init(Map<String, RemoteService> handlerMappings) {
        classHandlerMappings = new HashMap<>();
        for (String key : handlerMappings.keySet()) {
            RemoteService remoteService = handlerMappings.get(key);
            Class<? extends RemoteService> clazz = remoteService.getClass();
            classHandlerMappings.put(clazz, remoteService);
        }
    }

    @Override
    public RequestProcessorFactory getRequestProcessorFactory(Class cls)
            throws XmlRpcException {
        final RemoteService remoteService = classHandlerMappings.get(cls);
        return request -> remoteService;
    }
}
