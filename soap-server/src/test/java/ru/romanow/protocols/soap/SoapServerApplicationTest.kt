package ru.romanow.protocols.soap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.romanow.protocols.soap.web.WebServiceDocumentLiteral;
import ru.romanow.protocols.soap.web.WebServiceDocumentLiteralWrapped;
import ru.romanow.protocols.soap.web.WebServiceRpcLiteral;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SoapServerApplicationTest {

    @Autowired
    private WebServiceRpcLiteral webServiceRpcLiteral;

    @Autowired
    private WebServiceDocumentLiteral webServiceDocumentLiteral;

    @Autowired
    private WebServiceDocumentLiteralWrapped webServiceDocumentLiteralWrapped;

    @Test
    void testApp() {
        assertThat(webServiceRpcLiteral).isNotNull();
        assertThat(webServiceDocumentLiteral).isNotNull();
        assertThat(webServiceDocumentLiteralWrapped).isNotNull();
    }
}