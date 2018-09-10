package ru.romanow.protocols.soap.web;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.romanow.protocols.soap.generated.literal.model.TestObjectRequest;
import ru.romanow.protocols.soap.generated.literal.model.TestObjectResponse;
import ru.romanow.protocols.soap.generated.literal.model.WebServiceDocumentLiteral;

/**
 * Created by ronin on 25.09.16
 */
public class DocumentLiteralWebServiceClient
        implements WebServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(WebServiceClient.class);

    private final WebServiceDocumentLiteral webService;

    public DocumentLiteralWebServiceClient(WebServiceDocumentLiteral webService) {
        this.webService = webService;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void makeRequest() {
        TestObjectRequest request = new TestObjectRequest();
        request.setId(RandomUtils.nextInt(0, 1000));
        request.setSearchString(RandomStringUtils.randomAlphabetic(10));

        logger.info("Request [{}]", request);

        TestObjectResponse response = webService.processRequest(request);
        logger.info("Response [{}]", response);
    }
}
