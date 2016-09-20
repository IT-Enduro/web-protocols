package ru.romanow.protocols.rpc.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.soap.model.TestObjectRequest;
import ru.romanow.protocols.soap.model.TestObjectResponse;

/**Î
 * Created by ronin on 20.09.16
 */
@Service
public class TestService
        implements RemoteService {
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Override
    public TestObjectResponse processRequest(TestObjectRequest request) {
        logger.info("Request to RemoteService.processRequest {}", request);
        TestObjectResponse response = new TestObjectResponse()
                .setCode(RandomUtils.nextInt(0, 100))
                .setData(RandomStringUtils.randomAlphanumeric(10));
        logger.info("Response from RemoteService.processRequest {}", response);
        return response;
    }

    @Override
    public int sum(int a, int b) {
        logger.info("Request to RemoteService.sum {} and {}", a, b);
        int sum = a + b;
        logger.info("Response from RemoteService.sum is {} + {} = {}", a, b, sum);
        return sum;
    }
}
