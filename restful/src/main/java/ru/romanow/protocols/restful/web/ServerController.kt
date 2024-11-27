package ru.romanow.protocols.restful.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.Operation
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
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.ServersResponse
import ru.romanow.protocols.common.server.service.ServerService
import java.util.concurrent.TimeUnit

@Tag(name = "Server API")
@RestController
@RequestMapping("/api/v1/servers")
class ServerController(
    private val serverService: ServerService,
    private val objectMapper: ObjectMapper
) {

    @Operation(summary = "Get server by Id")
    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun getById(@PathVariable id: Int): ResponseEntity<ServerResponse> = ok()
        .cacheControl(CacheControl.noCache())
        .body(serverService.getById(id))

    @Operation(summary = "Find all servers")
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun all(): ResponseEntity<ServersResponse> {
        val servers = serverService.all()
        return ok()
            .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
            .eTag(DigestUtils.md5DigestAsHex(objectMapper.writeValueAsBytes(servers)))
            .body(ServersResponse(servers))
    }

    @Operation(summary = "Find servers in city")
    @GetMapping(
        params = ["city"],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun findInCity(@RequestParam city: String) = ServersResponse(serverService.findInCity(city))

    @Operation(summary = "Save new server")
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

    @Operation(summary = "Full update server by Id")
    @PutMapping(
        value = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun fullUpdate(@PathVariable id: Int, @Valid @RequestBody request: CreateServerRequest) =
        serverService.update(id, request)

    @Operation(summary = "Edit server by Id")
    @PatchMapping(
        value = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun partialUpdate(@PathVariable id: Int, @RequestBody request: CreateServerRequest) =
        serverService.update(id, request)

    @Operation(summary = "Delete server by Id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        serverService.delete(id)
    }
}
