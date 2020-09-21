package ru.romanow.protocols.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romanow.protocols.soap.generated.literal.model.WebServiceDocumentLiteralImplService;
import ru.romanow.protocols.soap.generated.rpc.model.WebServiceRpcLiteralImplService;
import ru.romanow.protocols.soap.generated.wrapped.model.WebServiceDocumentLiteralWrappedImplService;
import ru.romanow.protocols.soap.web.DocumentLiteralWebServiceClient;
import ru.romanow.protocols.soap.web.DocumentWrappedWebServiceClient;
import ru.romanow.protocols.soap.web.RpcLiteralWebServiceClient;
import ru.romanow.protocols.soap.web.WebServiceClient;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class WebServiceConfiguration {

    @Bean
    public WebServiceClient documentEncodedWebServiceClient() {
        WebServiceDocumentLiteralWrappedImplService service = new WebServiceDocumentLiteralWrappedImplService();
        return new DocumentWrappedWebServiceClient(service.getWebServiceDocumentLiteralWrappedImplPort());
    }

    @Bean
    public WebServiceClient documentLiteralWebServiceClient() {
        WebServiceDocumentLiteralImplService service = new WebServiceDocumentLiteralImplService();
        return new DocumentLiteralWebServiceClient(service.getWebServiceDocumentLiteralImplPort());
    }

    @Bean
    public WebServiceClient rpcLiteralWebServiceClient() {
        WebServiceRpcLiteralImplService service = new WebServiceRpcLiteralImplService();
        return new RpcLiteralWebServiceClient(service.getWebServiceRpcLiteralImplPort());
    }
}
