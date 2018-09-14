package ru.romanow.protocols.consumer.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(
        ids = "ru.romanow.protocols:producer:+:stubs:8080",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class PingServiceTest {

    @Autowired
    private PingService pingService;

    @Test
    public void testMakeOperation() {
        boolean available = pingService.ping();
        assertTrue(available);
    }
}
