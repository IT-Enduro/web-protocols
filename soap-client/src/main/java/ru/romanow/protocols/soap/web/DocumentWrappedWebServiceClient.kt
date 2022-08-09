package ru.romanow.protocols.soap.web

import com.google.common.base.MoreObjects
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.RandomUtils
import org.slf4j.LoggerFactory
import ru.romanow.protocols.soap.generated.wrapped.model.TestObjectRequest
import ru.romanow.protocols.soap.generated.wrapped.model.WebServiceDocumentWrapped

class DocumentWrappedWebServiceClient(
    private val webService: WebServiceDocumentWrapped
) : WebServiceClient {
    private val logger = LoggerFactory.getLogger(WebServiceClient::class.java)

    override fun makeRequest() {
        val request = TestObjectRequest()
        request.id = RandomUtils.nextInt(0, 1000)
        request.searchString = RandomStringUtils.randomAlphabetic(10)
        logger.info(
            "Request [{}]", MoreObjects.toStringHelper(request)
                .add("id", request.id)
                .add("searchString", request.searchString)
                .toString()
        )
        val response = webService.processRequest(request)
        logger.info(
            "Response [{}]", MoreObjects.toStringHelper(response)
                .add("code", response.code)
                .add("data", response.data)
                .toString()
        )
    }
}