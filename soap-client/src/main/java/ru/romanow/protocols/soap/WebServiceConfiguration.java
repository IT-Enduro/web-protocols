package ru.romanow.protocols.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romanow.protocols.soap.generated.literal.model.WebServiceDocumentLiteralImplService;
import ru.romanow.protocols.soap.generated.wrapped.model.WebServiceDocumentLiteralWrappedImplService;
import ru.romanow.protocols.soap.web.DocumentLiteralWebServiceClient;
import ru.romanow.protocols.soap.web.DocumentWrappedWebServiceClient;
import ru.romanow.protocols.soap.web.WebServiceClient;

/**
 * Created by ronin on 25.09.16
 */
@Configuration
public class WebServiceConfiguration {

    @Bean
    public WebServiceClient documentEncodedWebServiceClient() {
        WebServiceDocumentLiteralWrappedImplService service =
                new WebServiceDocumentLiteralWrappedImplService();
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
