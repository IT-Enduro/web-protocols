package ru.romanow.protocols.rpc.service.spring;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.AbstractReflectiveHandlerMapping;
import ru.romanow.protocols.rpc.service.Handler;

import java.util.Map;

/**
 * Created by ronin on 20.09.16
 */
public class SpringHandlerMapping
        extends AbstractReflectiveHandlerMapping {

    public void setHandlerMappings(Map<String, Handler> handlerMappings)
            throws XmlRpcException {
        SpringRequestProcessorFactoryFactory ff =
                (SpringRequestProcessorFactoryFactory)getRequestProcessorFactoryFactory();

        ff.init(handlerMappings);
        for (String serviceName : handlerMappings.keySet()) {
            Handler serviceBean = handlerMappings.get(serviceName);
            registerPublicMethods(serviceName, serviceBean.getClass());
        }
    }
}