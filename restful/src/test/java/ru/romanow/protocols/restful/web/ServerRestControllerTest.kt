package ru.romanow.protocols.restful.web

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.absent
import com.github.tomakehurst.wiremock.client.WireMock.or
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import jakarta.transaction.Transactional
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
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.Purpose
import ru.romanow.protocols.common.domain.Server
import ru.romanow.protocols.common.domain.State
import ru.romanow.protocols.common.repository.ServerRepository
import ru.romanow.protocols.common.repository.StateRepository
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
class ServerRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private var objectMapper = jacksonObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    @Autowired
    private lateinit var stateRepository: StateRepository

    @Autowired
    private lateinit var serverRepository: ServerRepository

    @Test
    fun testGetById() {
        val state = State(city = CITY, country = COUNTRY)
        var server = Server(
            address = "Yerevan",
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
            .andExpect(jsonPath("$.address").value(server.address))
            .andExpect(jsonPath("$.purpose").value(server.purpose?.name))
            .andExpect(jsonPath("$.latency").value(server.latency))
            .andExpect(jsonPath("$.bandwidth").value(server.bandwidth))
            .andExpect(jsonPath("$.stateId").value(server.state?.id))
            .andDo(verify())
            .andDo(
                document(
                    "Get Server by ID",
                    pathParameters(parameterWithName("id").description("Server ID")),
                    responseFieldsSnippet()
                )
            )
    }

    @Test
    fun testState() {
        val state = State(city = CITY, country = COUNTRY)
        var server = Server(
            address = "Yerevan",
            purpose = Purpose.DATABASE,
            latency = nextInt(1000),
            bandwidth = nextInt(1000),
            state = state
        )
        server = serverRepository.saveAndFlush(server)

        mockMvc.perform(
            get("/api/v1/servers/{id}/state", server.id)
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
                    "Get Server State by ID",
                    pathParameters(parameterWithName("id").description("Server ID")),
                    responseFields(
                        fieldWithPath("id").description("ID"),
                        fieldWithPath("city").description("City"),
                        fieldWithPath("country").description("Country")
                    )
                )
            )
    }

    @Test
    fun testAll() {
        val state = State(city = CITY, country = COUNTRY)
        var server = Server(
            address = "Yerevan",
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
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].address").value(server.address))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].purpose").value(server.purpose?.name))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].latency").value(server.latency))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].bandwidth").value(server.bandwidth))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].stateId").value(server.state?.id))
            .andDo(verify())
            .andDo(document("Get all Servers", responseFieldsSnippet("servers[].")))
    }

    @Test
    fun testFindByAddress() {
        val state = State(city = CITY, country = COUNTRY)
        var server = Server(
            address = "Yerevan",
            purpose = Purpose.DATABASE,
            latency = nextInt(1000),
            bandwidth = nextInt(1000),
            state = state
        )
        server = serverRepository.saveAndFlush(server)

        mockMvc.perform(
            get("/api/v1/servers")
                .queryParam("address", server.address)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.servers").isArray)
            .andExpect(jsonPath("$.servers.length()").value(1))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].id").value(server.id))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].address").value(server.address))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].purpose").value(server.purpose?.name))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].latency").value(server.latency))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].bandwidth").value(server.bandwidth))
            .andExpect(jsonPath("$.servers[?(@.id == ${server.id})].stateId").value(server.state?.id))
            .andDo(verify())
            .andDo(
                document(
                    "Find Server by address",
                    queryParameters(parameterWithName("address").description("Datacenter address")),
                    responseFieldsSnippet("servers[].")
                )
            )
    }

    @Test
    fun testCreate() {
        val state = stateRepository.saveAndFlush(State(city = CITY, country = COUNTRY))
        val request = CreateServerRequest(
            address = CITY,
            purpose = Purpose.BACKEND.name,
            latency = nextInt(100),
            bandwidth = nextInt(1000),
            stateId = state.id
        )

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
                        .withRequestBody(match("$.address", regex("\\S+")))
                        .withRequestBody(match("$.purpose", regex("(BACKEND|FRONTEND|DATABASE)")))
                        .withRequestBody(match("$.latency", regex("\\d{1,3}")))
                        .withRequestBody(match("$.bandwidth", regex("\\d{1,8}")))
                        .withRequestBody(match("$.stateId", regex("\\d+")))
                )
            )
            .andDo(document("Create new Server", requestFieldsSnippet()))

    }

    @Test
    fun testFullUpdate() {
        var state = stateRepository.saveAndFlush(State(city = "Moscow", country = "Russia"))
        var server = Server(
            address = "Moscow",
            purpose = Purpose.DATABASE,
            latency = nextInt(1000),
            bandwidth = nextInt(1000),
            state = state
        )
        server = serverRepository.saveAndFlush(server)

        state = stateRepository.saveAndFlush(State(city = CITY, country = COUNTRY))
        val request = CreateServerRequest(
            address = CITY,
            purpose = Purpose.BACKEND.name,
            latency = nextInt(100),
            bandwidth = nextInt(1000),
            stateId = state.id
        )

        mockMvc.perform(
            put("/api/v1/servers/{id}", server.id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(server.id))
            .andExpect(jsonPath("$.address").value(request.address))
            .andExpect(jsonPath("$.purpose").value(request.purpose))
            .andExpect(jsonPath("$.latency").value(request.latency))
            .andExpect(jsonPath("$.bandwidth").value(request.bandwidth))
            .andExpect(jsonPath("$.stateId").value(request.stateId))
            .andDo(
                verify().wiremock(
                    WireMock.put(urlEqualTo("/api/v1/servers/${server.id}"))
                        .withRequestBody(match("$.address", regex("\\S+")))
                        .withRequestBody(match("$.purpose", regex("(BACKEND|FRONTEND|DATABASE)")))
                        .withRequestBody(match("$.latency", regex("\\d{1,3}")))
                        .withRequestBody(match("$.bandwidth", regex("\\d{1,8}")))
                        .withRequestBody(match("$.stateId", regex("\\d+")))
                )
            )
            .andDo(
                document(
                    "Full update Server",
                    pathParameters(parameterWithName("id").description("Server ID")),
                    requestFieldsSnippet(),
                    responseFieldsSnippet()
                )
            )
    }

    @Test
    fun testPartialUpdate() {
        val state = stateRepository.saveAndFlush(State(city = CITY, country = COUNTRY))
        var server = Server(
            address = CITY,
            purpose = Purpose.DATABASE,
            latency = nextInt(1000),
            bandwidth = nextInt(1000),
            state = state
        )
        server = serverRepository.saveAndFlush(server)

        val request = CreateServerRequest(
            address = CITY,
            purpose = Purpose.BACKEND.name,
            latency = nextInt(100),
            bandwidth = nextInt(1000),
            stateId = state.id
        )

        mockMvc.perform(
            patch("/api/v1/servers/{id}", server.id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(server.id))
            .andExpect(jsonPath("$.address").value(request.address))
            .andExpect(jsonPath("$.purpose").value(request.purpose))
            .andExpect(jsonPath("$.latency").value(request.latency))
            .andExpect(jsonPath("$.bandwidth").value(request.bandwidth))
            .andExpect(jsonPath("$.stateId").value(request.stateId))
            .andDo(
                verify().wiremock(
                    WireMock.patch(urlEqualTo("/api/v1/servers/${server.id}"))
                        .withRequestBody(match("$.address", or(regex("\\S+"), absent())))
                        .withRequestBody(match("$.purpose", or(regex("(BACKEND|FRONTEND|DATABASE)"), absent())))
                        .withRequestBody(match("$.latency", or(regex("\\d{1,3}"), absent())))
                        .withRequestBody(match("$.bandwidth", or(regex("\\d{1,8}"), absent())))
                        .withRequestBody(match("$.stateId", or(regex("\\d+"), absent())))
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
        val state = State(city = CITY, country = COUNTRY)
        var server = Server(
            address = "Yerevan",
            purpose = Purpose.DATABASE,
            latency = nextInt(1000),
            bandwidth = nextInt(1000),
            state = state
        )
        server = serverRepository.saveAndFlush(server)

        mockMvc.perform(delete("/api/v1/servers/{id}", server.id))
            .andExpect(status().isNoContent)
            .andDo(verify())
            .andDo(document("Remove Server by ID", pathParameters(parameterWithName("id").description("Server ID"))))
    }

    private fun requestFieldsSnippet() = requestFields(
        fieldWithPath("address").description("Datacenter address").type(JsonFieldType.STRING).optional(),
        fieldWithPath("purpose").description("Server purpose").type(JsonFieldType.STRING).optional(),
        fieldWithPath("latency").description("Server latency").optional().type(JsonFieldType.NUMBER),
        fieldWithPath("bandwidth").description("Server bandwidth").optional().type(JsonFieldType.NUMBER),
        fieldWithPath("stateId").description("State ID").optional().type(JsonFieldType.NUMBER),
    )

    private fun responseFieldsSnippet(prefix: String = "") = responseFields(
        fieldWithPath("${prefix}id").description("ID").type(JsonFieldType.NUMBER),
        fieldWithPath("${prefix}address").description("Datacenter address").type(JsonFieldType.STRING),
        fieldWithPath("${prefix}purpose").description("Server purpose").type(JsonFieldType.STRING),
        fieldWithPath("${prefix}latency").description("Server latency").type(JsonFieldType.NUMBER),
        fieldWithPath("${prefix}bandwidth").description("Server bandwidth").type(JsonFieldType.NUMBER),
        fieldWithPath("${prefix}stateId").description("State ID").type(JsonFieldType.NUMBER),
    )

    companion object {
        private const val CITY = "Yerevan"
        private const val COUNTRY = "Armenia"
    }
}