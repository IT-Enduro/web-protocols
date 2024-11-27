package ru.romanow.protocols.soap.service

import org.springframework.stereotype.Service
import ru.romanow.protocols.common.client.service.ServerClient
import ru.romanow.protocols.soap.client.CreateServerRequest
import ru.romanow.protocols.soap.client.ServerWebService
import ru.romanow.protocols.soap.client.StateInfo

@Service
class SoapServerClient(
    private val serverWebService: ServerWebService
) : ServerClient {

    override fun create(purpose: String): String {
        val request = ru.romanow.protocols.soap.client.CreateServerRequest()
            .also { it.purpose = purpose }
        return serverWebService.create(request).toString()
    }

    override fun findAll(): String {
        val response = serverWebService.all()
        return response.servers.joinToString { it.toString() }
    }

    override fun getById(id: Int): String {
        return serverWebService.getById(id).toString()
    }

    override fun findInCity(city: String): String {
        val response = serverWebService.findInCity(city)
        return response.servers.joinToString { it.toString() }
    }

    override fun update(
        id: Int, purpose: String?, latency: Int?, bandwidth: Int?, city: String?, country: String?
    ): String {
        val request = CreateServerRequest().also {
            it.purpose = purpose
            it.bandwidth = bandwidth
            it.latency = latency
            it.state = StateInfo().also { st ->
                st.city = city
                st.city = city
            }
        }
        return serverWebService.update(id, request).toString()
    }

    override fun delete(id: Int) {
        serverWebService.delete(id)
    }
}
