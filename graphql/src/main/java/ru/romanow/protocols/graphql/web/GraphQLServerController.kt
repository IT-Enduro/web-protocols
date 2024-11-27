package ru.romanow.protocols.graphql.web

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.common.server.service.ServerService

@Controller
class GraphQLServerController(
    private val serverService: ServerService
) {

    @QueryMapping("getById")
    fun getById(@Argument("id") id: Int) = serverService.getById(id)

    @QueryMapping("findAll")
    fun findAll() = serverService.all()

    @QueryMapping("findInCity")
    fun findInCity(@Argument("city") city: String) = serverService.findInCity(city)

    @MutationMapping("create")
    fun create(@Argument("request") request: CreateServerRequest) = serverService.create(request)

    @MutationMapping("update")
    fun update(@Argument("id") id: Int, @Argument("request") request: CreateServerRequest) =
        serverService.update(id, request)

    @MutationMapping("delete")
    fun delete(@Argument("id") id: Int) {
        serverService.delete(id)
    }

    @SchemaMapping(typeName = "ServerResponse", field = "serversInCity")
    fun serversInCity(response: ServerResponse): Int {
        return serverService.findInCity(response.state.city!!).size
    }
}
