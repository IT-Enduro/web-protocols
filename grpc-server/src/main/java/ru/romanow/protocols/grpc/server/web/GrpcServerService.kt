package ru.romanow.protocols.grpc.server.web

import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import jakarta.validation.ConstraintViolationException
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import ru.romanow.protocols.api.model.CreateServerRequest
import ru.romanow.protocols.api.model.ServerResponse
import ru.romanow.protocols.api.model.StateInfo
import ru.romanow.protocols.api.model.UpdateServerRequest
import ru.romanow.protocols.common.server.service.ServerService
import ru.romanow.protocols.grpc.ServerServiceGrpc
import ru.romanow.protocols.grpc.ServerServiceModels
import ru.romanow.protocols.grpc.ServerServiceModels.*
import ru.romanow.protocols.grpc.server.interceptors.LogInterceptor

@GrpcService(interceptors = [LogInterceptor::class])
class GrpcServerService(
    private val serverService: ServerService,
    private val validator: LocalValidatorFactoryBean
) : ServerServiceGrpc.ServerServiceImplBase() {

    override fun getById(request: ID, responseObserver: StreamObserver<ServerServiceModels.ServerResponse>) {
        val server = serverService.getById(request.id)
        responseObserver.onNext(buildServerResponse(server))
        responseObserver.onCompleted()
    }

    override fun findAll(request: Empty, responseObserver: StreamObserver<ServersResponse>) {
        val servers = serverService.all().map { buildServerResponse(it) }
        responseObserver.onNext(ServersResponse.newBuilder().addAllServer(servers).build())
        responseObserver.onCompleted()
    }

    override fun findInCity(request: City, responseObserver: StreamObserver<ServersResponse>) {
        val servers = serverService.findInCity(request.city).map { buildServerResponse(it) }
        responseObserver.onNext(ServersResponse.newBuilder().addAllServer(servers).build())
        responseObserver.onCompleted()
    }

    override fun create(
        request: ServerServiceModels.CreateServerRequest,
        responseObserver: StreamObserver<ServerServiceModels.ServerResponse>
    ) {
        val createServerRequest = CreateServerRequest().apply {
            purpose = request.purpose.name
            latency = request.latency
            bandwidth = request.bandwidth
            state = StateInfo().apply {
                city = request.state.city
                country = request.state.country
            }
        }
        val violations = validator.validate(createServerRequest)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }

        val server = serverService.create(createServerRequest)
        responseObserver.onNext(buildServerResponse(server))
        responseObserver.onCompleted()
    }

    override fun update(
        request: ServerServiceModels.UpdateServerRequest,
        responseObserver: StreamObserver<ServerServiceModels.ServerResponse>
    ) {
        val updateServerRequest = UpdateServerRequest().apply {
            purpose = request.purpose.name
            latency = request.latency
            bandwidth = request.bandwidth
            state = StateInfo().apply {
                city = request.state.city
                country = request.state.country
            }
        }
        val violations = validator.validate(updateServerRequest)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }
        val server = serverService.update(id = request.id, request = updateServerRequest)
        responseObserver.onNext(buildServerResponse(server))
        responseObserver.onCompleted()
    }

    override fun delete(request: ID, responseObserver: StreamObserver<Empty>) {
        serverService.delete(request.id)
        responseObserver.onNext(Empty.newBuilder().build())
        responseObserver.onCompleted()
    }

    private fun buildServerResponse(server: ServerResponse): ServerServiceModels.ServerResponse {
        val state = ServerServiceModels.StateInfo
            .newBuilder()
            .setCity(server.state?.city)
            .setCountry(server.state?.country)
        return ServerServiceModels.ServerResponse.newBuilder()
            .setPurpose(Purpose.valueOf(server.purpose!!.name))
            .setLatency(server.latency!!)
            .setBandwidth(server.bandwidth!!)
            .setState(state)
            .build()
    }
}
