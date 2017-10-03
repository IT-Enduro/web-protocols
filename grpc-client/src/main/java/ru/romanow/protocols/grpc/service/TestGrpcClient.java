package ru.romanow.protocols.grpc.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.grpc.TestServiceGrpc;
import ru.romanow.protocols.grpc.TestServiceOuterClass;

/**
 * Created by romanow on 03.10.17.
 */
@Service
public class TestGrpcClient {

    private TestServiceGrpc.TestServiceBlockingStub testService;

    public TestGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .build();

        testService = TestServiceGrpc.newBlockingStub(channel);
    }

    public TestServiceOuterClass.TestResponse testClient() {
        TestServiceOuterClass.TestRequest.Builder builder =
                TestServiceOuterClass.TestRequest.newBuilder();
        TestServiceOuterClass.TestRequest request = builder
                .setSize(3)
                .setMessages(0, "Hello")
                .setMessages(1, "World")
                .build();

        return testService.simpleRequest(request);
    }
}
