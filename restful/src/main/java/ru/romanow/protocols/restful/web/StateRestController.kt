package ru.romanow.protocols.restful.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest
import ru.romanow.protocols.api.model.CreateStateRequest
import ru.romanow.protocols.api.model.StatesResponse
import ru.romanow.protocols.common.service.StateService

@Tag(name = "State API")
@RestController
@RequestMapping("/api/v1/states")
class StateRestController(
    private val stateService: StateService,
) {

    @Operation(summary = "Get state by Id")
    @GetMapping(value = ["/{id}"], produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
    fun getById(@PathVariable id: Int) = stateService.getById(id)

    @Operation(summary = "Find all states")
    @GetMapping(produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
    fun all() = StatesResponse(stateService.all())

    @Operation(summary = "Save new state")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        consumes = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE],
        produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun create(@Valid @RequestBody request: CreateStateRequest): ResponseEntity<Void> {
        val id = stateService.create(request)
        return ResponseEntity.created(
            fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri()
        ).build()
    }

    @Operation(summary = "Full update state by Id")
    @PutMapping(
        value = ["/{id}"],
        consumes = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE],
        produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun fullUpdate(@PathVariable id: Int, @Valid @RequestBody request: CreateStateRequest) =
        stateService.edit(id, request)

    @Operation(summary = "Edit state by Id")
    @PatchMapping(
        value = ["/{id}"],
        consumes = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE],
        produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun partialUpdate(@PathVariable id: Int, @RequestBody request: CreateStateRequest) =
        stateService.edit(id, request)

    @Operation(summary = "Delete state by Id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        stateService.delete(id)
    }
}