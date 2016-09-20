package ru.romanow.protocols.rpc.service.spring;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory.StatelessProcessorFactoryFactory;
import ru.romanow.protocols.rpc.service.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ronin on 20.09.16
 */
public class SpringRequestProcessorFactoryFactory
        extends StatelessProcessorFactoryFactory
        implements RequestProcessorFactoryFactory {

    private Map<Class<? extends Handler>, Handler> classHandlerMappings;

    protected void init(Map<String,Handler> handlerMappings) {
        classHandlerMappings = new HashMap<>();
        for (String key : handlerMappings.keySet()) {
            Handler handler = handlerMappings.get(key);
            Class<? extends Handler> clazz = handler.getClass();
            classHandlerMappings.put(clazz, handler);
        }
    }

    @Override
    public RequestProcessorFactory getRequestProcessorFactory(Class cls)
            throws XmlRpcException {
        final Handler handler = classHandlerMappings.get(cls);
        return pRequest -> handler;
    }
}
