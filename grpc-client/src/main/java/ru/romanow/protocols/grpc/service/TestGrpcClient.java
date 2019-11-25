package ru.romanow.protocols.grpc.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.grpc.TestServiceGrpc;
import ru.romanow.protocols.grpc.TestServiceOuterClass;

import static ru.romanow.protocols.grpc.TestServiceOuterClass.TestRequest.newBuilder;

@Service
public class TestGrpcClient {

    private ManagedChannel channel;
    private TestServiceGrpc.TestServiceBlockingStub testService;

    public TestGrpcClient() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        testService = TestServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() {
        channel.shutdownNow();
    }

    public TestServiceOuterClass.TestResponse testClient() {
        TestServiceOuterClass.TestRequest request = newBuilder()
                .setSize(2)
                .addMessage("Hello")
                .addMessage("World")
                .build();

        return testService.simpleRequest(request);
    }
}
