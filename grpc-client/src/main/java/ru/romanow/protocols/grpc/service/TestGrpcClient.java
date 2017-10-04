package ru.romanow.protocols.grpc.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.grpc.TestServiceGrpc;
import ru.romanow.protocols.grpc.TestServiceOuterClass;

import static ru.romanow.protocols.grpc.TestServiceOuterClass.TestRequest.newBuilder;

/**
 * Created by romanow on 03.10.17.
 */
@Service
public class TestGrpcClient {

    private TestServiceGrpc.TestServiceBlockingStub testService;

    public TestGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext(true)
                .build();

        testService = TestServiceGrpc.newBlockingStub(channel);
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
