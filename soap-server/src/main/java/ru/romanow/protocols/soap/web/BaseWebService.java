package ru.romanow.protocols.soap.web;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;

public class BaseWebService {

    public TestObjectResponse processRequest(TestObjectRequest request) {
        return new TestObjectResponse()
                .setCode(RandomUtils.nextInt(0, 100))
                .setData(RandomStringUtils.randomAlphanumeric(20));
    }
}
