package ru.romanow.protocols.soap.web

import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.RandomUtils
import ru.romanow.protocols.api.model.TestObjectRequest
import ru.romanow.protocols.api.model.TestObjectResponse

open class BaseWebService {
    fun processRequest(request: TestObjectRequest): TestObjectResponse =
        TestObjectResponse(
            code = RandomUtils.nextInt(0, 100),
            data = RandomStringUtils.randomAlphanumeric(20)
        )
}