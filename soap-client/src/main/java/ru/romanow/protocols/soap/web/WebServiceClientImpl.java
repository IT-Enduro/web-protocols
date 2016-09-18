package ru.romanow.protocols.soap.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.soap.generated.model.TestObjectRequest;
import ru.romanow.protocols.soap.generated.model.TestObjectResponse;
import ru.romanow.protocols.soap.generated.model.WebServiceDocumentLiteralWrappedImplService;
import ru.romanow.protocols.soap.generated.model.WebServiceDocumentWrapped;

/**
 * Created by ronin on 18.09.16
 */
@Service
public class WebServiceClientImpl
        implements WebServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(WebServiceClient.class);

    private final WebServiceDocumentWrapped webService;

    public WebServiceClientImpl() {
        WebServiceDocumentLiteralWrappedImplService service =
                new WebServiceDocumentLiteralWrappedImplService();
        webService = service.getWebServiceDocumentLiteralWrappedImplPort();
    }

    @Override
    public TestObjectResponse makeRequest(TestObjectRequest request) {
        logger.info("Request [{}]", request);

        TestObjectResponse response = webService.processRequest(request);
        logger.info("Response [{}]", response);

        return response;
    }
}
