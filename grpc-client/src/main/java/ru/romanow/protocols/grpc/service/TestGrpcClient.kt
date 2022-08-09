package ru.romanow.protocols.grpc.service

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Service
import ru.romanow.protocols.grpc.TestServiceGrpc
import ru.romanow.protocols.grpc.TestServiceGrpc.TestServiceBlockingStub
import ru.romanow.protocols.grpc.TestServiceOuterClass.TestRequest
import ru.romanow.protocols.grpc.TestServiceOuterClass.TestResponse
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class TestGrpcClient {

    private lateinit var testService: TestServiceBlockingStub
    private lateinit var channel: ManagedChannel

    @PostConstruct
    fun init() {
        channel = ManagedChannelBuilder
            .forAddress("localhost", 6565).disableServiceConfigLookUp()
            .usePlaintext()
            .build()
        testService = TestServiceGrpc.newBlockingStub(channel)
    }

    fun testClient(): TestResponse {
        val request = TestRequest.newBuilder()
            .setSize(2)
            .addMessage("Hello")
            .addMessage("World")
            .build()
        return testService.simpleRequest(request)
    }

    @PreDestroy
    fun shutdown() {
        channel.shutdownNow()
    }
}