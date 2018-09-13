package ru.romanow.protocols.soap.web;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import ru.romanow.protocols.api.model.TestObjectRequest;
import ru.romanow.protocols.api.model.TestObjectResponse;

/**
 * Created by ronin on 16.09.16
 */
public class BaseWebService {

    public TestObjectResponse processRequest(TestObjectRequest request) {
        return new TestObjectResponse()
                .setCode(RandomUtils.nextInt(0, 100))
                .setData(RandomStringUtils.randomAlphanumeric(20));
    }
}
