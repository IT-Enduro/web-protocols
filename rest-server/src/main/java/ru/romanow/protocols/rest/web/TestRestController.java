package ru.romanow.protocols.rest.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.*;
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

    @ApiOperation(value = "Ping", httpMethod = "GET", response = String.class)
    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

    @ApiOperation(value = "Set cookies", httpMethod = "GET", response = String.class)
    @GetMapping("/cookies")
    public String setCookies(HttpServletResponse response) {
        response.addCookie(createCookie("TestCookie", RandomStringUtils.randomAlphabetic(5)));
        return "ok";
    }

    @ApiOperation(value = "Operation return simple response", httpMethod = "POST")
    @ApiResponse(code = 200, message = "Success", response = TestObjectResponse.class)
    @PostMapping("/process")
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
