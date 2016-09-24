package ru.romanow.protocols.rest.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.romanow.core.commons.annotations.LogRequest;
import ru.romanow.protocols.soap.model.TestObjectRequest;
import ru.romanow.protocols.soap.model.TestObjectResponse;
import ru.romanow.protocols.soap.model.XmlTestObjectResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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

    @ApiOperation(value = "Set cookies", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/cookies", method = RequestMethod.GET)
    public String setCookies(HttpServletResponse response) {
        response.addCookie(createCookie("TestCookie", RandomStringUtils.randomAlphabetic(5)));
        return "ok";
    }

    @LogRequest(classLogger = TestRestController.class)
    @ApiOperation(value = "Operation return simple response", httpMethod = "POST")
    @ApiResponse(code = 200, message = "Success", response = TestObjectResponse.class)
    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public XmlTestObjectResponse processRequest(
            @ApiParam(value = "Request object", required = true)
            @RequestBody TestObjectRequest request) {
        return new XmlTestObjectResponse()
                .setCode(RandomUtils.nextInt(0, 100))
                .setData(RandomStringUtils.randomAlphanumeric(10));
    }

    private Cookie createCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        return cookie;
    }
}
