package ru.romanow.protocols.soap.web

import jakarta.jws.WebMethod
import jakarta.jws.WebParam
import jakarta.jws.WebResult
import jakarta.jws.WebService
import jakarta.jws.soap.SOAPBinding
import jakarta.jws.soap.SOAPBinding.ParameterStyle.WRAPPED
import jakarta.jws.soap.SOAPBinding.Style.DOCUMENT
import jakarta.jws.soap.SOAPBinding.Use.LITERAL
import org.springframework.stereotype.Controller
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.ServersResponse
import ru.romanow.protocols.common.server.service.ServerService

@Controller
@WebService
@SOAPBinding(style = DOCUMENT, use = LITERAL, parameterStyle = WRAPPED)
class ServerWebService(
    private val serverService: ServerService
) {

    @WebResult(name = "ServersResponse")
    @WebMethod(operationName = "getById")
    fun getById(@WebParam(name = "id") id: Int): ServerResponse {
        return serverService.getById(id)
    }

    @WebResult(name = "ServersResponse")
    @WebMethod(operationName = "all")
    fun all(): ServersResponse {
        return ServersResponse(serverService.all())
    }

    @WebResult(name = "ServersResponse")
    @WebMethod(operationName = "findInCity")
    fun findInCity(@WebParam(name = "city") city: String) = ServersResponse(serverService.findInCity(city))

    @WebResult(name = "ServerResponse")
    @WebMethod(operationName = "create")
    fun create(@WebParam(name = "request") request: CreateServerRequest): ServerResponse {
        return serverService.create(request)
    }

    @WebResult(name = "ServerResponse")
    @WebMethod(operationName = "update")
    fun update(@WebParam(name = "id") id: Int, @WebParam(name = "request") request: CreateServerRequest): ServerResponse {
        return serverService.update(id, request)
    }

    @WebMethod(operationName = "delete")
    fun delete(@WebParam(name = "id") id: Int) {
        serverService.delete(id)
    }
}
