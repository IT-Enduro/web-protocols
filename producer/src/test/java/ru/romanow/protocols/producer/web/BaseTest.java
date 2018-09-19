package ru.romanow.protocols.producer.web;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseTest {
    private static final String OUTPUT_DIR = "build/generated-snippets";

    @Autowired
    private ExceptionController exceptionController;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(OUTPUT_DIR);

    @Rule
    public TestName testName = new TestName();

    @Before
    public void init() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder =
                standaloneSetup(controller())
                        .setControllerAdvice(exceptionController)
                        .apply(documentationConfiguration(restDocumentation))
                        .alwaysDo(document(getClass().getSimpleName() + "_" + testName.getMethodName()));

        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

    protected abstract Object controller();
}
