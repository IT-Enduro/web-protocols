package ru.romanow.protocols.grpc.web;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import ru.romanow.protocols.grpc.TestServiceGrpc;
import ru.romanow.protocols.grpc.TestServiceOuterClass;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.romanow.protocols.grpc.TestServiceOuterClass.TestResponse.newBuilder;

/**
 * Created by romanow on 03.10.17.
 */
@GRpcService
public class TestGrpcService
        extends TestServiceGrpc.TestServiceImplBase {

    @Override
    public void simpleRequest(TestServiceOuterClass.TestRequest request, StreamObserver<TestServiceOuterClass.TestResponse> responseObserver) {
        long start = System.currentTimeMillis();
        String result = request.getMessageList()
                               .stream()
                               .collect(Collectors.joining(", "));

        TestServiceOuterClass.TestResponse.Builder builder = newBuilder();

        for (int i = 0; i < request.getSize(); i++) {
            builder.addResultMessage(result);
        }

        long duration = System.currentTimeMillis() - start;
        builder.setDuration(duration)
                .setStatus(TestServiceOuterClass.Status.DONE);

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
