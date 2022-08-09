package ru.romanow.protocols.rest.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest
@AutoConfigureRestDocs
class PingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testPing() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ping"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.response").value("OK"))
            .andDo(
                MockMvcRestDocumentation.document(
                    "ping",
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("response").description("Available status")
                            .type(JsonFieldType.STRING)
                    )
                )
            )
    }

    @Test
    fun testSetCookie() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cookies"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.cookie().exists("TestCookie"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.response").value("OK"))
            .andDo(
                MockMvcRestDocumentation.document(
                    "cookies",
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("response").description("Available status")
                            .type(JsonFieldType.STRING)
                    )
                )
            )
    }
}