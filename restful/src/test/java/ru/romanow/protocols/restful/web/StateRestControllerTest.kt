package ru.romanow.protocols.restful.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.absent
import com.github.tomakehurst.wiremock.client.WireMock.or
import jakarta.transaction.Transactional
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.restdocs.WireMockRestDocs.verify
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.romanow.protocols.api.model.CreateStateRequest
import ru.romanow.protocols.common.domain.State
import ru.romanow.protocols.common.repository.StateRepository
import ru.romanow.protocols.restful.config.DatabaseTestConfiguration
import com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath as match
import com.github.tomakehurst.wiremock.matching.RegexPattern as regex

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@AutoConfigureTestEntityManager
@Import(DatabaseTestConfiguration::class)
class StateRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var stateRepository: StateRepository

    @Test
    fun testGetById() {
        val state = stateRepository
            .saveAndFlush(State(city = CITY, country = COUNTRY))

        mockMvc.perform(
            get("/api/v1/states/{id}", state.id)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(state.id))
            .andExpect(jsonPath("$.city").value(state.city))
            .andExpect(jsonPath("$.country").value(state.country))
            .andDo(verify())
            .andDo(
                document(
                    "Get State by ID",
                    pathParameters(parameterWithName("id").description("State ID")),
                    responseFieldsSnippet()
                )
            )
    }

    @Test
    fun testAll() {
        val state = stateRepository
            .saveAndFlush(State(city = CITY, country = COUNTRY))

        mockMvc.perform(get("/api/v1/states"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.states").isArray)
            .andExpect(jsonPath("$.states.length()").value(3))
            .andExpect(jsonPath("$.states[?(@.id == ${state.id})].id").value(state.id))
            .andExpect(jsonPath("$.states[?(@.id == ${state.id})].city").value(state.city))
            .andExpect(jsonPath("$.states[?(@.id == ${state.id})].country").value(state.country))
            .andDo(verify())
            .andDo(document("Get all States", responseFieldsSnippet("states[].")))
    }

    @Test
    fun testCreate() {
        val request = CreateStateRequest(city = CITY, country = COUNTRY)

        mockMvc.perform(
            post("/api/v1/states")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(header().string(HttpHeaders.LOCATION, matchesRegex("http://localhost:\\d+/api/v1/states/\\d+")))
            .andDo(
                verify().wiremock(
                    WireMock.post(WireMock.urlEqualTo("/api/v1/states"))
                        .withRequestBody(match("$.city", regex("\\S+")))
                        .withRequestBody(match("$.country", regex("\\S+")))
                )
            )
            .andDo(document("Create new State", requestFieldsSnippet()))
    }

    @Test
    fun testFullUpdate() {
        val state = stateRepository
            .saveAndFlush(State(city = "Moscow", country = "Russia"))
        val request = CreateStateRequest(city = CITY, country = COUNTRY)

        mockMvc.perform(
            put("/api/v1/states/{id}", state.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(state.id))
            .andExpect(jsonPath("$.city").value(CITY))
            .andExpect(jsonPath("$.country").value(COUNTRY))
            .andDo(
                verify().wiremock(
                    WireMock.put(WireMock.urlEqualTo("/api/v1/states/${state.id}"))
                        .withRequestBody(match("$.city", regex("\\S+")))
                        .withRequestBody(match("$.country", regex("\\S+")))
                )
            )
            .andDo(document("Full update State", requestFieldsSnippet(), responseFieldsSnippet()))
    }

    @Test
    fun testPartialUpdate() {
        val state = stateRepository
            .saveAndFlush(State(city = "Moscow", country = "Russia"))
        val request = CreateStateRequest(city = CITY, country = COUNTRY)

        mockMvc.perform(
            patch("/api/v1/states/{id}", state.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(state.id))
            .andExpect(jsonPath("$.city").value(CITY))
            .andExpect(jsonPath("$.country").value(COUNTRY))
            .andDo(
                verify().wiremock(
                    WireMock.patch(WireMock.urlEqualTo("/api/v1/states/${state.id}"))
                        .withRequestBody(match("$.city", or(regex("\\S+"), absent())))
                        .withRequestBody(match("$.country", or(regex("\\S+"), absent())))
                )
            )
            .andDo(document("Partial update State", requestFieldsSnippet(), responseFieldsSnippet()))
    }

    @Test
    fun testDelete() {
        val state = stateRepository
            .saveAndFlush(State(city = CITY, country = COUNTRY))

        mockMvc.perform(delete("/api/v1/states/{id}", state.id))
            .andExpect(status().isNoContent)
            .andDo(verify())
            .andDo(document("Remove State by ID", pathParameters(parameterWithName("id").description("State ID"))))
    }

    private fun requestFieldsSnippet() = requestFields(
        fieldWithPath("city").description("City").type(JsonFieldType.STRING).optional(),
        fieldWithPath("country").description("Country").type(JsonFieldType.STRING).optional(),
    )

    private fun responseFieldsSnippet(prefix: String = "") = responseFields(
        fieldWithPath("${prefix}id").description("ID").type(JsonFieldType.NUMBER),
        fieldWithPath("${prefix}city").description("City").type(JsonFieldType.STRING),
        fieldWithPath("${prefix}country").description("Country").type(JsonFieldType.STRING),
    )

    companion object {
        private const val CITY = "Yerevan"
        private const val COUNTRY = "Armenia"
    }
}