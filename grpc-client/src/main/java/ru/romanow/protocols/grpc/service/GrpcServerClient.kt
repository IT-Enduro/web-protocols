package ru.romanow.protocols.grpc.service

import com.google.protobuf.Empty
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service
import ru.romanow.protocols.common.client.service.ServerClient
import ru.romanow.protocols.grpc.ServerServiceGrpc.ServerServiceBlockingStub
import ru.romanow.protocols.grpc.ServerServiceModels.*

@Service
class GrpcServerClient : ServerClient {

    @GrpcClient("default")
    private lateinit var serverService: ServerServiceBlockingStub

    override fun create(purpose: String): String {
        val request = CreateServerRequest.newBuilder()
            .setLatency(10)
            .setBandwidth(10000)
            .setPurpose(Purpose.valueOf(purpose))
            .setState(StateInfo.newBuilder().setCity("Moscow").setCountry("Russia"))
            .build()
        return serverService.create(request).toString()
    }

    override fun findAll() = serverService
        .findAll(Empty.newBuilder().build())
        .toString()

    override fun getById(id: Int) = serverService
        .getById(ID.newBuilder().setId(id).build())
        .toString()

    override fun findInCity(city: String) = serverService
        .findInCity(City.newBuilder().setCity(city).build())
        .toString()

    override fun update(
        id: Int,
        purpose: String?,
        latency: Int?,
        bandwidth: Int?,
        city: String?,
        country: String?
    ): String {
        val request = UpdateServerRequest.newBuilder().setId(id)
        purpose?.let { request.setPurpose(Purpose.valueOf(it)) }
        latency?.let { request.setLatency(it) }
        bandwidth?.let { request.setBandwidth(bandwidth) }
        val state = StateInfo.newBuilder()
        city?.let { state.setCity(it) }
        country?.let { state.setCountry(it) }
        request.setState(state)
        return serverService.update(request.build()).toString()
    }

    override fun delete(id: Int) {
        serverService.delete(ID.newBuilder().setId(id).build())
    }
}
