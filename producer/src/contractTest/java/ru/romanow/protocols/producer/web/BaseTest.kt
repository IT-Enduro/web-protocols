package ru.romanow.protocols.producer.web

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

@ExtendWith(RestDocumentationExtension::class)
@SpringBootTest
abstract class BaseTest {

    @Autowired
    private lateinit var exceptionController: ExceptionController

    @Rule
    var testName = TestName()

    @BeforeEach
    fun init(provider: RestDocumentationContextProvider) {
        RestAssuredMockMvc.mockMvc(
            standaloneSetup(controller())
                .setControllerAdvice(exceptionController)
                .apply<StandaloneMockMvcBuilder>(documentationConfiguration(provider))
                .alwaysDo<StandaloneMockMvcBuilder>(document(javaClass.simpleName + "_" + testName.methodName))
                .build()
        )
    }

    protected abstract fun controller(): Any

    companion object {
        private const val OUTPUT_DIR = "build/generated-snippets"
    }
}