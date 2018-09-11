package ru.romanow.protocols.consumer.web;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.romanow.protocols.soap.model.PingResponse;
import ru.romanow.protocols.soap.model.TestObjectRequest;
import ru.romanow.protocols.soap.model.TestObjectResponse;

@RestController
@RequestMapping("/api")
public class TestRestController {

    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PingResponse ping() {
        return new PingResponse("ok");
    }

    @PostMapping(value = "/process",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TestObjectResponse processRequest(@RequestBody TestObjectRequest request) {
        return new TestObjectResponse()
                .setCode(RandomUtils.nextInt(0, 100))
                .setData(RandomStringUtils.randomAlphanumeric(10));
    }
}
