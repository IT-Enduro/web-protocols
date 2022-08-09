package ru.romanow.protocols.soap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romanow.protocols.soap.generated.literal.model.WebServiceDocumentLiteral;
import ru.romanow.protocols.soap.generated.literal.model.WebServiceDocumentLiteralImplService;
import ru.romanow.protocols.soap.generated.rpc.model.WebServiceRpcLiteral;
import ru.romanow.protocols.soap.generated.rpc.model.WebServiceRpcLiteralImplService;
import ru.romanow.protocols.soap.generated.wrapped.model.WebServiceDocumentLiteralWrappedImplService;
import ru.romanow.protocols.soap.generated.wrapped.model.WebServiceDocumentWrapped;
import ru.romanow.protocols.soap.service.SOAPLoggingHandler;
import ru.romanow.protocols.soap.web.DocumentLiteralWebServiceClient;
import ru.romanow.protocols.soap.web.DocumentWrappedWebServiceClient;
import ru.romanow.protocols.soap.web.RpcLiteralWebServiceClient;
import ru.romanow.protocols.soap.web.WebServiceClient;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.util.List;

@Configuration
public class WebServiceConfiguration {

    @Bean
    public WebServiceClient documentEncodedWebServiceClient() {
        final WebServiceDocumentLiteralWrappedImplService service = new WebServiceDocumentLiteralWrappedImplService();
        final WebServiceDocumentWrapped port = service.getWebServiceDocumentLiteralWrappedImplPort();
        configureLogging((BindingProvider) port);
        return new DocumentWrappedWebServiceClient(port);
    }

    @Bean
    public WebServiceClient documentLiteralWebServiceClient() {
        WebServiceDocumentLiteralImplService service = new WebServiceDocumentLiteralImplService();
        final WebServiceDocumentLiteral port = service.getWebServiceDocumentLiteralImplPort();
        configureLogging((BindingProvider) port);
        return new DocumentLiteralWebServiceClient(port);
    }

    @Bean
    public WebServiceClient rpcLiteralWebServiceClient() {
        WebServiceRpcLiteralImplService service = new WebServiceRpcLiteralImplService();
        final WebServiceRpcLiteral port = service.getWebServiceRpcLiteralImplPort();
        configureLogging((BindingProvider) port);
        return new RpcLiteralWebServiceClient(port);
    }

    private void configureLogging(BindingProvider port) {
        final Binding binding = port.getBinding();
        final List<Handler> handlerList = binding.getHandlerChain();
        handlerList.add(new SOAPLoggingHandler());
        binding.setHandlerChain(handlerList);
    }
}
