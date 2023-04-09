package ru.romanow.protocols.grpc.server.web

import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.StateInfo
import ru.romanow.protocols.common.server.service.ServerService
import ru.romanow.protocols.grpc.ServerServiceGrpc
import ru.romanow.protocols.grpc.TestService
import ru.romanow.protocols.grpc.TestService.City
import ru.romanow.protocols.grpc.TestService.Empty
import ru.romanow.protocols.grpc.TestService.ID
import ru.romanow.protocols.grpc.TestService.Purpose
import ru.romanow.protocols.grpc.server.interceptors.LogInterceptor

@GRpcService(interceptors = [LogInterceptor::class])
class GrpcServerService(
    private val serverService: ServerService
) : ServerServiceGrpc.ServerServiceImplBase() {

    override fun getById(request: ID, responseObserver: StreamObserver<TestService.ServerResponse>) {
        val server = serverService.getById(request.id)
        responseObserver.onNext(buildServerResponse(server))
        responseObserver.onCompleted()
    }

    override fun findAll(request: Empty, responseObserver: StreamObserver<TestService.ServersResponse>) {
        val servers = serverService.all()
            .map { buildServerResponse(it) }
        responseObserver.onNext(TestService.ServersResponse.newBuilder().addAllServers(servers).build())
        responseObserver.onCompleted()
    }

    override fun findInCity(request: City, responseObserver: StreamObserver<TestService.ServersResponse>) {
        val servers = serverService.findInCity(request.city)
            .map { buildServerResponse(it) }
        responseObserver.onNext(TestService.ServersResponse.newBuilder().addAllServers(servers).build())
        responseObserver.onCompleted()
    }

    override fun create(
        request: TestService.CreateServerRequest,
        responseObserver: StreamObserver<TestService.ServerResponse>
    ) {
        val createServerRequest = CreateServerRequest(
            purpose = request.purpose.name,
            latency = request.latency,
            bandwidth = request.bandwidth,
            state = StateInfo(city = request.state.city, country = request.state.country)
        )
        val server = serverService.create(createServerRequest)
        responseObserver.onNext(buildServerResponse(server))
        responseObserver.onCompleted()
    }

    override fun update(
        request: TestService.CreateServerRequest,
        responseObserver: StreamObserver<TestService.ServerResponse>
    ) {
        val createServerRequest = CreateServerRequest(
            purpose = request.purpose.name,
            latency = request.latency,
            bandwidth = request.bandwidth,
            state = StateInfo(city = request.state.city, country = request.state.country)
        )
        val server = serverService.update(id = request.id, request = createServerRequest)
        responseObserver.onNext(buildServerResponse(server))
        responseObserver.onCompleted()
    }

    private fun buildServerResponse(server: ServerResponse): TestService.ServerResponse {
        val state = TestService.StateInfo
            .newBuilder()
            .setCity(server.state.city)
            .setCity(server.state.country)
        return TestService.ServerResponse.newBuilder()
            .setPurpose(Purpose.valueOf(server.purpose.name))
            .setLatency(server.latency)
            .setBandwidth(server.bandwidth)
            .setState(state)
            .build()
    }
}