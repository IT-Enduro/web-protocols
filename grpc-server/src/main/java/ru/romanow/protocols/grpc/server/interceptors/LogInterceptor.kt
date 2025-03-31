package ru.romanow.protocols.grpc.server.interceptors

import io.grpc.ForwardingServerCall.SimpleForwardingServerCall
import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener
import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import org.slf4j.LoggerFactory


class LogInterceptor : ServerInterceptor {
    private val logger = LoggerFactory.getLogger(LogInterceptor::class.java)

    override fun <REQ, RESP> interceptCall(
        call: ServerCall<REQ, RESP>, headers: Metadata, next: ServerCallHandler<REQ, RESP>
    ): ServerCall.Listener<REQ> {
        logger.info("Call method '${call.methodDescriptor.serviceName}::${call.methodDescriptor.bareMethodName}'")

        return object : SimpleForwardingServerCallListener<REQ>(
            next.startCall(
                object : SimpleForwardingServerCall<REQ, RESP>(call) {
                    override fun sendMessage(message: RESP) {
                        logger.info(
                            "Response '${call.methodDescriptor.serviceName}::${call.methodDescriptor.bareMethodName} " +
                                "response: [$message]"
                        )
                        super.sendMessage(message)
                    }
                },
                headers
            )
        ) {
            override fun onMessage(message: REQ) {
                logger.info(
                    "Request '${call.methodDescriptor.serviceName}::${call.methodDescriptor.bareMethodName} " +
                        "request: [$message]"
                )
                super.onMessage(message)
            }
        }
    }
}
