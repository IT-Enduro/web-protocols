package ru.romanow.protocols.consumer.service

import ru.romanow.protocols.api.model.TestObjectRequest
import ru.romanow.protocols.api.model.TestObjectResponse

interface OperationService {
    fun makeOperation(request: TestObjectRequest): TestObjectResponse
}