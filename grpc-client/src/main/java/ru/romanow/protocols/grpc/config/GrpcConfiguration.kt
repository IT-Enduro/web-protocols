package ru.romanow.protocols.grpc.config

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.romanow.protocols.common.client.properties.ServiceUrlProperties
import ru.romanow.protocols.grpc.ServerServiceGrpc

@Configuration
class GrpcConfiguration {

    @Bean(destroyMethod = "shutdownNow")
    fun channel(properties: ServiceUrlProperties): ManagedChannel {
        val address = properties.serverUrl.split(":")
        return ManagedChannelBuilder.forAddress(address[0], address[1].toInt())
            .disableServiceConfigLookUp()
            .usePlaintext()
            .build()
    }

    @Bean
    fun serverService(channel: ManagedChannel): ServerServiceGrpc.ServerServiceBlockingStub =
        ServerServiceGrpc.newBlockingStub(channel)
}