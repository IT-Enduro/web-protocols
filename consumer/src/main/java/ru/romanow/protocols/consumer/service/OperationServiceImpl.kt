package ru.romanow.protocols.consumer.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate
import ru.romanow.protocols.api.model.TestObjectRequest
import ru.romanow.protocols.api.model.TestObjectResponse
import ru.romanow.protocols.consumer.exception.RestRequestException

@Service
class OperationServiceImpl(
    @Value("\${consumer.address}")
    private val consumerAddress: String,
    private val restTemplate: RestTemplate,
) : OperationService {
    private val logger = LoggerFactory.getLogger(OperationService::class.java)

    override fun makeOperation(request: TestObjectRequest): TestObjectResponse {
        logger.debug("Make request to '{}' with params '{}'", OPERATION_PROCESS_PATH, request)
        return try {
            restTemplate.postForObject(
                consumerAddress + OPERATION_PROCESS_PATH,
                request,
                TestObjectResponse::class.java
            )!!
        } catch (exception: RestClientResponseException) {
            throw RestRequestException(OPERATION_PROCESS_PATH, exception.rawStatusCode, exception.statusText)
        }
    }

    companion object {
        private const val OPERATION_PROCESS_PATH = "/api/v1/operation/process"
    }
}