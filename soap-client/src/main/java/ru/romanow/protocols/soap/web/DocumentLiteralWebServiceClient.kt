package ru.romanow.protocols.soap.web

import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.RandomUtils
import org.slf4j.LoggerFactory
import ru.romanow.protocols.soap.generated.literal.model.TestObjectRequest
import ru.romanow.protocols.soap.generated.literal.model.WebServiceDocumentLiteral

class DocumentLiteralWebServiceClient(
    private val webService: WebServiceDocumentLiteral
) : WebServiceClient {
    private val logger = LoggerFactory.getLogger(WebServiceClient::class.java)

    override fun makeRequest() {
        val request = TestObjectRequest()
        request.id = RandomUtils.nextInt(0, 1000)
        request.searchString = RandomStringUtils.randomAlphabetic(10)
        logger.info("Request [id=${request.id}, searchString=${request.searchString}]")
        val response = webService.processRequest(request)
        logger.info("Response [code=${response.code}, data=${response.data}]")
    }
}