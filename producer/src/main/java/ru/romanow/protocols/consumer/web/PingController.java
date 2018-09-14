package ru.romanow.protocols.consumer.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanow.protocols.api.model.PingResponse;

@RestController
@RequestMapping("/api/ping")
public class PingController {
    private static final Logger logger = LoggerFactory.getLogger(PingController.class);

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PingResponse ping() {
        logger.info("Request to '/cookies'");
        return new PingResponse("ok");
    }
}
