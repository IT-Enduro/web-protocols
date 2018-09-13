package ru.romanow.protocols.rest.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanow.protocols.api.model.PingResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/api/ping", description = "Simple ping controller")
@RequestMapping("/api/ping")
public class PingController {
    private static final Logger logger = LoggerFactory.getLogger(PingController.class);

    @ApiOperation(value = "Ping",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = PingResponse.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PingResponse ping() {
        logger.info("Request to '/cookies'");
        return new PingResponse("ok");
    }

    @ApiOperation(value = "Set cookies",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = PingResponse.class)
    @GetMapping(value = "/cookies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PingResponse setCookies(HttpServletResponse response) {
        logger.info("Request to '/cookies'");
        response.addCookie(createCookie("TestCookie", RandomStringUtils.randomAlphabetic(5)));
        return new PingResponse("ok");
    }

    private Cookie createCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        return cookie;
    }
}
