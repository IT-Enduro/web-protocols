package ru.romanow.protocols.rest.web

import com.google.gson.Gson
import org.apache.commons.lang3.RandomStringUtils
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
import ru.romanow.protocols.api.model.TestObjectRequest

@WebMvcTest
@AutoConfigureRestDocs
class OperationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val gson = Gson()

    @Test
    fun testProcessRequest() {
        val id = 101
        val searchString = RandomStringUtils.randomAlphabetic(3)
        val request = TestObjectRequest(id, searchString)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/operation/process")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(CODE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(NAME))
            .andDo(
                MockMvcRestDocumentation.document(
                    "process",
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("id").description("Request id").type(JsonFieldType.NUMBER),
                        PayloadDocumentation.fieldWithPath("searchString").description("Search string")
                            .type(JsonFieldType.STRING)
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("code").description("Result code")
                            .type(JsonFieldType.NUMBER),
                        PayloadDocumentation.fieldWithPath("data").description("Payload").type(JsonFieldType.STRING)
                    )
                )
            )
    }

    @Test
    fun testProcessRequestFail() {
        val id = 1
        val searchString = RandomStringUtils.randomAlphabetic(3)
        val request = TestObjectRequest(id, searchString)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/operation/process")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isIAmATeapot)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Id '1' too low"))
    }

    companion object {
        private const val CODE = 100
        private const val NAME = "TEST"
    }
}