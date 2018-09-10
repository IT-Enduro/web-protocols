package ru.romanow.protocols.consumer;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.romanow.protocols.soap.model.TestObjectRequest;
import ru.romanow.protocols.soap.model.TestObjectResponse;

@RestController
public class TestRestController {

    @GetMapping("/ping")
    public String ping() {
        return "ok";
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
