package ru.romanow.protocols.consumer.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.romanow.protocols.soap.model.PingResponse;
import ru.romanow.protocols.soap.model.TestObjectRequest;
import ru.romanow.protocols.soap.model.TestObjectResponse;

@RestController
@RequestMapping("/api")
public class TestRestController {
    private static final Integer CODE = 100;
    private static final String DATA = "ABCDEFGH";

    @GetMapping(value = "/ping",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PingResponse> ping(@RequestParam(value = "fail", required = false, defaultValue = "false") Boolean fail) {
        return fail
                ? ResponseEntity.unprocessableEntity().contentType(MediaType.APPLICATION_JSON_UTF8).build()
                : ResponseEntity.ok(new PingResponse("ok"));
    }

    @PostMapping(value = "/process",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TestObjectResponse processRequest(@RequestBody TestObjectRequest request) {
        return new TestObjectResponse(CODE, DATA);
    }
}
