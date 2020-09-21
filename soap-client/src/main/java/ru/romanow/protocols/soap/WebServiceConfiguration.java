package ru.romanow.protocols.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romanow.protocols.soap.generated.literal.model.WebServiceDocumentLiteralImplService;
import ru.romanow.protocols.soap.generated.wrapped.model.WebServiceDocumentLiteralWrappedImplService;
import ru.romanow.protocols.soap.web.DocumentLiteralWebServiceClient;
import ru.romanow.protocols.soap.web.DocumentWrappedWebServiceClient;
import ru.romanow.protocols.soap.web.WebServiceClient;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class WebServiceConfiguration {
    private static final String DOCUMENT_SERVICE_LOCATION = "http://localhost:8880/ws/document-literal?wsdl";

    @Bean
    public WebServiceClient documentEncodedWebServiceClient()
            throws MalformedURLException {
        WebServiceDocumentLiteralWrappedImplService service =
                new WebServiceDocumentLiteralWrappedImplService(new URL(DOCUMENT_SERVICE_LOCATION));
        return new DocumentWrappedWebServiceClient(
                service.getWebServiceDocumentLiteralWrappedImplPort());
    }

    @Bean
    public WebServiceClient documentLiteralWebServiceClient() {
        WebServiceDocumentLiteralImplService service =
                new WebServiceDocumentLiteralImplService();
        return new DocumentLiteralWebServiceClient(
                service.getWebServiceDocumentLiteralImplPort());
    }
}
