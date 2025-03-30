package ru.romanow.protocols.restful.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.util.DigestUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest
import ru.romanow.protocols.api.model.*
import ru.romanow.protocols.common.server.service.ServerService
import java.util.concurrent.TimeUnit

@Tag(name = "Servers API")
@RestController
@RequestMapping("/api/v1/servers")
class ServerController(
    private val serverService: ServerService,
    private val objectMapper: ObjectMapper
) {

    @Operation(
        summary = "Найти сервер по Id",
        responses = [
            ApiResponse(responseCode = "200", description = "Информация о сервере"),
            ApiResponse(
                responseCode = "404", description = "Сервер не найден по Id",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun getById(@PathVariable id: Int): ResponseEntity<ServerResponse> = ok()
        .cacheControl(CacheControl.noCache())
        .body(serverService.getById(id))

    @Operation(
        summary = "Найти все сервера",
        responses = [ApiResponse(responseCode = "200", description = "Список серверов")]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun all(): ResponseEntity<ServersResponse> {
        val servers = serverService.all()
        return ok()
            .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
            .eTag(DigestUtils.md5DigestAsHex(objectMapper.writeValueAsBytes(servers)))
            .body(ServersResponse(servers))
    }

    @Operation(
        summary = "Найти сервера в городе",
        responses = [ApiResponse(responseCode = "200", description = "Список серверов")]
    )
    @GetMapping(
        params = ["city"],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun findInCity(@RequestParam city: String) = ServersResponse(serverService.findInCity(city))

    @Operation(
        summary = "Создать новый сервер",
        responses = [
            ApiResponse(
                responseCode = "201", description = "Сервер успешно создан",
                headers = [Header(name = "Location", description = "Ссылка на созданный сервер")]
            ),
            ApiResponse(
                responseCode = "400", description = "Некорректные параметры запроса",
                content = [Content(schema = Schema(implementation = ValidationErrorResponse::class))]
            ),
        ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun create(@Valid @RequestBody request: CreateServerRequest): ResponseEntity<Void> {
        val id = serverService.create(request).id
        return ResponseEntity
            .created(
                fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(id)
                    .toUri()
            )
            .build()
    }

    @Operation(
        summary = "Редактировать данные сервера по Id",
        responses = [
            ApiResponse(responseCode = "200", description = "Сервер успешно обновлен"),
            ApiResponse(
                responseCode = "400", description = "Некорректные параметры запроса",
                content = [Content(schema = Schema(implementation = ValidationErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Сервер не найден по Id",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PatchMapping(
        value = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun update(@PathVariable id: Int, @Valid @RequestBody request: UpdateServerRequest) =
        serverService.update(id, request)

    @Operation(
        summary = "Удалить сервер по Id",
        responses = [ApiResponse(responseCode = "204", description = "Сервер успешно удален")]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        serverService.delete(id)
    }
}
