package ru.romanow.protocols.rest.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanow.protocols.api.model.PingResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@Tag(name = "/api/ping")
@RequestMapping("/api/ping")
public class PingController {
    private static final Logger logger = LoggerFactory.getLogger(PingController.class);

    @Operation(description = "Ping")
    @ApiResponse(content = @Content(mediaType = APPLICATION_JSON_UTF8_VALUE, schema = @Schema(implementation = PingResponse.class)))
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public PingResponse ping() {
        logger.info("Request to '/ping'");
        return new PingResponse("ok");
    }

    @Operation(description = "Set cookies")
    @ApiResponse(content = @Content(mediaType = APPLICATION_JSON_UTF8_VALUE, schema = @Schema(implementation = PingResponse.class)))
    @GetMapping(value = "/cookies", produces = APPLICATION_JSON_UTF8_VALUE)
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
