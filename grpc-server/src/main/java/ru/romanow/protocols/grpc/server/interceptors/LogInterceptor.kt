package ru.romanow.protocols.grpc.server.interceptors

import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import org.slf4j.LoggerFactory

class LogInterceptor : ServerInterceptor {
    private val logger = LoggerFactory.getLogger(LogInterceptor::class.java)

    override fun <REQ : Any, RESP : Any> interceptCall(
        call: ServerCall<REQ, RESP>, headers: Metadata, next: ServerCallHandler<REQ, RESP>
    ): ServerCall.Listener<REQ> {
        logger.info("Call method '{}::{}'", call.methodDescriptor.serviceName, call.methodDescriptor.bareMethodName)
        return next.startCall(call, headers)
    }
}
