package ru.romanow.protocols.rest.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.romanow.core.commons.annotations.LogRequest;
import ru.romanow.protocols.soap.model.TestObjectRequest;
import ru.romanow.protocols.soap.model.TestObjectResponse;

/**
 * Created by ronin on 20.09.16
 */
@Api(value = "/", description = "Simple rest controller")
@RestController
public class TestRestController {
    private static final Logger logger = LoggerFactory.getLogger(TestRestController.class);

    @ApiOperation(value = "Ping", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "ok";
    }

    @ApiOperation(value = "Operation return simple response", httpMethod = "POST")
    @ApiResponse(code = 200, message = "Success", response = TestObjectResponse.class)
    @LogRequest(classLogger = TestRestController.class)
    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public TestObjectResponse processRequest(
            @ApiParam(value = "Request object", required = true)
            @RequestBody TestObjectRequest request) {
        return new TestObjectResponse()
                .setCode(RandomUtils.nextInt(0, 100))
                .setData(RandomStringUtils.randomAlphanumeric(10));
    }
}
