package ru.romanow.protocols.grpc.web;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import ru.romanow.protocols.grpc.TestServiceGrpc;
import ru.romanow.protocols.grpc.TestServiceOuterClass;

/**
 * Created by romanow on 03.10.17.
 */
@GRpcService
public class TestGrpcService
        extends TestServiceGrpc.TestServiceImplBase {

    @Override
    public void simpleRequest(TestServiceOuterClass.TestRequest request, StreamObserver<TestServiceOuterClass.TestResponse> responseObserver) {
        super.simpleRequest(request, responseObserver);
    }
}
