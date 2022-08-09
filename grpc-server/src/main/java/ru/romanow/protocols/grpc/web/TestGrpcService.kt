package ru.romanow.protocols.grpc.web

import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import ru.romanow.protocols.grpc.TestServiceGrpc.TestServiceImplBase
import ru.romanow.protocols.grpc.TestServiceOuterClass
import ru.romanow.protocols.grpc.TestServiceOuterClass.TestRequest
import ru.romanow.protocols.grpc.TestServiceOuterClass.TestResponse
import java.lang.String

@GRpcService
class TestGrpcService : TestServiceImplBase() {

    override fun simpleRequest(request: TestRequest, responseObserver: StreamObserver<TestResponse>) {
        val start = System.currentTimeMillis()
        val result = String.join(", ", request.messageList)
        val builder = TestResponse.newBuilder()
        for (i in 0 until request.size) {
            builder.addResultMessage(result)
        }
        val duration = System.currentTimeMillis() - start
        builder.setDuration(duration).status = TestServiceOuterClass.Status.DONE
        responseObserver.onNext(builder.build())
        responseObserver.onCompleted()
    }
}