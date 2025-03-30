package ru.romanow.protocols.restful.web

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.absent
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.or
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import org.hamcrest.Matchers.matchesRegex
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.restdocs.WireMockRestDocs.verify
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders.LOCATION
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.Purpose
import ru.romanow.protocols.api.model.StateInfo
import ru.romanow.protocols.common.server.domain.Server
import ru.romanow.protocols.common.server.domain.State
import ru.romanow.protocols.common.server.repository.ServerRepository
import ru.romanow.protocols.restful.config.DatabaseTestConfiguration
import kotlin.random.Random.Default.nextInt
import com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath as match
import com.github.tomakehurst.wiremock.matching.RegexPattern as regex

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@AutoConfigureTestEntityManager
@Import(DatabaseTestConfiguration::class)
class ServerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private var objectMapper = jacksonObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    @Autowired
    private lateinit var serverRepository: ServerRepository

    @Test
    fun testGetById() {
        val state = State(city = CITY, country = COUNTRY)
        var server = Server(
            purpose = Purpose.DATABASE,
            latency = nextInt(1000),
            bandwidth = nextInt(1000),
            state = state
        )
        server = serverRepository.saveAndFlush(server)

        mockMvc.perform(
            get("/api/v1/servers/{id}", server.id)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(server.id))
            .andExpect(jsonPath("$.purpose").value(server.purpose?.name))
            .andExpect(jsonPath("$.latency").value(server.latency))
            .andExpect(jsonPath("$.bandwidth").value(server.bandwidth))
            .andExpect(jsonPath("$.state.id").value(server.state?.id))
            .andExpect(jsonPath("$.state.city").value(server.state?.city))
            .andExpect(jsonPath("$.state.country").value(server.state?.country))
            .andDo(
                verify().wiremock(
                    WireMock.get(urlMatching("/api/v1/servers/\\d+"))
                )
            )
            .andDo(
                document(
                    "Get Server by ID",
                    pathParameters(parameterWithName("id").description("Server ID")),
                    responseFieldsSnippet()
                )
            )
    }

    @Test
    fun testAll() {
        val state = State(city = CITY, country = COUNTRY)
        var server = Server(
            purpose = Purpose.DATABASE,
            latency = nextInt(1000),
            bandwidth = nextInt(1000),
            state = state
        )
        server = serverRepository.saveAndFlush(server)

        mockMvc.perform(
            get("/api/v1/servers", server.id)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.servers").isArray)
            .andExpect(jsonPath("$.servers.length()").value(5))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].id").value(server.id))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].purpose").value(server.purpose?.name))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].latency").value(server.latency))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].bandwidth").value(server.bandwidth))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].state.id").value(server.state?.id))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].state.city").value(server.state?.city))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].state.country").value(server.state?.country))
            .andDo(verify())
            .andDo(document("Get all Servers", responseFieldsSnippet("servers[].")))
    }

    @Test
    fun testFindInCity() {
        val state = State(city = CITY, country = COUNTRY)
        var server = Server(
            purpose = Purpose.DATABASE,
            latency = nextInt(1000),
            bandwidth = nextInt(1000),
            state = state
        )
        server = serverRepository.saveAndFlush(server)

        mockMvc.perform(
            get("/api/v1/servers")
                .queryParam("city", CITY)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.servers").isArray)
            .andExpect(jsonPath("$.servers.length()").value(1))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].id").value(server.id))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].purpose").value(server.purpose?.name))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].latency").value(server.latency))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].bandwidth").value(server.bandwidth))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].state.id").value(server.state?.id))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].state.city").value(server.state?.city))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].state.country").value(server.state?.country))
            .andDo(verify())
            .andDo(
                document(
                    "Find Servers in city",
                    queryParameters(parameterWithName("city").description("City")),
                    responseFieldsSnippet("servers[].")
                )
            )
    }

    @Test
    fun testCreate() {
        val request = CreateServerRequest().apply {
            purpose = Purpose.BACKEND.name
            latency = nextInt(100)
            bandwidth = nextInt(1000)
            state = StateInfo().apply {
                city = CITY
                country = COUNTRY
            }
        }

        mockMvc.perform(
            post("/api/v1/servers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(header().string(LOCATION, matchesRegex("http://localhost:\\d+/api/v1/servers/\\d+")))
            .andDo(
                verify().wiremock(
                    WireMock.post(urlEqualTo("/api/v1/servers"))
                        .withRequestBody(match("$.purpose", regex("(BACKEND|FRONTEND|DATABASE)")))
                        .withRequestBody(match("$.latency", regex("\\d{1,3}")))
                        .withRequestBody(match("$.bandwidth", regex("\\d{1,8}")))
                        .withRequestBody(match("$.state.city", equalTo(CITY)))
                        .withRequestBody(match("$.state.country", equalTo(COUNTRY)))
                )
            )
            .andDo(document("Create new Server", requestFieldsSnippet()))
    }

    @Test
    fun testPartialUpdate() {
        var server = Server(
            purpose = Purpose.DATABASE,
            latency = nextInt(1000),
            bandwidth = nextInt(1000),
            state = State(city = CITY, country = COUNTRY)
        )
        server = serverRepository.saveAndFlush(server)

        val request = CreateServerRequest().apply {
            purpose = Purpose.BACKEND.name
            latency = nextInt(100)
            bandwidth = nextInt(1000)
            state = StateInfo().apply {
                city = CITY
                country = COUNTRY
            }
        }

        mockMvc.perform(
            patch("/api/v1/servers/{id}", server.id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(server.id))
            .andExpect(jsonPath("$.purpose").value(request.purpose))
            .andExpect(jsonPath("$.latency").value(request.latency))
            .andExpect(jsonPath("$.bandwidth").value(request.bandwidth))
            .andExpect(jsonPath("$.state.id").value(server.state?.id))
            .andExpect(jsonPath("$.state.city").value(server.state?.city))
            .andExpect(jsonPath("$.state.country").value(server.state?.country))
            .andDo(
                verify().wiremock(
                    WireMock.patch(urlMatching("/api/v1/servers/\\d+"))
                        .withRequestBody(match("$.purpose", or(regex("(BACKEND|FRONTEND|DATABASE)"), absent())))
                        .withRequestBody(match("$.latency", or(regex("\\d{1,3}"), absent())))
                        .withRequestBody(match("$.bandwidth", or(regex("\\d{1,8}"), absent())))
                        .withRequestBody(match("$.state.city", equalTo(CITY)))
                        .withRequestBody(match("$.state.country", equalTo(COUNTRY)))
                )
            )
            .andDo(
                document(
                    "Partial update Server",
                    pathParameters(parameterWithName("id").description("Server ID")),
                    requestFieldsSnippet(),
                    responseFieldsSnippet()
                )
            )
    }

    @Test
    fun testDelete() {
        var server = Server(
            purpose = Purpose.DATABASE,
            latency = nextInt(1000),
            bandwidth = nextInt(1000),
            state = State(city = CITY, country = COUNTRY)
        )
        server = serverRepository.saveAndFlush(server)

        mockMvc.perform(delete("/api/v1/servers/{id}", server.id))
            .andExpect(status().isNoContent)
            .andDo(
                verify().wiremock(
                    WireMock.delete(urlMatching("/api/v1/servers/\\d+"))
                )
            )
            .andDo(document("Remove Server by ID", pathParameters(parameterWithName("id").description("Server ID"))))
    }

    private fun requestFieldsSnippet() = requestFields(
        fieldWithPath("purpose").description("Server purpose").type(STRING).optional(),
        fieldWithPath("latency").description("Server latency").optional().type(NUMBER),
        fieldWithPath("bandwidth").description("Server bandwidth").optional().type(NUMBER),
        fieldWithPath("state.city").description("City").optional().type(STRING),
        fieldWithPath("state.country").description("Country").optional().type(STRING),
    )

    private fun responseFieldsSnippet(prefix: String = "") = responseFields(
        fieldWithPath("${prefix}id").description("ID").type(NUMBER),
        fieldWithPath("${prefix}purpose").description("Server purpose").type(STRING),
        fieldWithPath("${prefix}latency").description("Server latency").type(NUMBER),
        fieldWithPath("${prefix}bandwidth").description("Server bandwidth").type(NUMBER),
        fieldWithPath("${prefix}state.id").description("State ID").type(NUMBER),
        fieldWithPath("${prefix}state.city").description("City").type(STRING),
        fieldWithPath("${prefix}state.country").description("Country").type(STRING),
    )

    companion object {
        private const val CITY = "Rostov"
        private const val COUNTRY = "Russia"
    }
}
