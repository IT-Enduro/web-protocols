package ru.romanow.protocols.grpc.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import ru.romanow.protocols.grpc.TestServiceGrpc;
import ru.romanow.protocols.grpc.TestServiceOuterClass;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static ru.romanow.protocols.grpc.TestServiceOuterClass.TestRequest.newBuilder;

@Service
public class TestGrpcClient {
    private TestServiceGrpc.TestServiceBlockingStub testService;
    private ManagedChannel channel;

    @PostConstruct
    public void init() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 6565).disableServiceConfigLookUp()
                .usePlaintext()
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

    @PreDestroy
    public void shutdown() {
        channel.shutdownNow();
    }
}
