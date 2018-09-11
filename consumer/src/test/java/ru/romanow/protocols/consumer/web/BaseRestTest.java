package ru.romanow.protocols.consumer.web;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseRestTest {

    @Autowired
    private TestRestController controller;

    @Before
    public void init() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = standaloneSetup(controller);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}
