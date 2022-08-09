package ru.romanow.protocols.soap

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.romanow.protocols.soap.web.WebServiceDocumentLiteral
import ru.romanow.protocols.soap.web.WebServiceDocumentLiteralWrapped
import ru.romanow.protocols.soap.web.WebServiceRpcLiteral

@SpringBootTest
internal class SoapServerApplicationTest {

    @Autowired
    private lateinit var webServiceRpcLiteral: WebServiceRpcLiteral

    @Autowired
    private lateinit var webServiceDocumentLiteral: WebServiceDocumentLiteral

    @Autowired
    private lateinit var webServiceDocumentLiteralWrapped: WebServiceDocumentLiteralWrapped

    @Test
    fun testApp() {
        Assertions.assertThat(webServiceRpcLiteral).isNotNull
        Assertions.assertThat(webServiceDocumentLiteral).isNotNull
        Assertions.assertThat(webServiceDocumentLiteralWrapped).isNotNull
    }
}