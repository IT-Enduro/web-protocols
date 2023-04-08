package ru.romanow.protocols.soap.config

import jakarta.xml.ws.BindingProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.romanow.protocols.soap.client.ServerWebService
import ru.romanow.protocols.soap.client.ServerWebServiceService
import ru.romanow.protocols.soap.utils.SOAPLoggingHandler

@Configuration
class WebServiceConfiguration {

    @Bean
    fun serverWebService(): ServerWebService {
        val service = ServerWebServiceService()
        val port = service.serverWebServicePort
        configureLogging(port as BindingProvider)
        return port
    }

    private fun configureLogging(port: BindingProvider) {
        val binding = port.binding
        val handlerList = binding.handlerChain
        handlerList.add(SOAPLoggingHandler())
        binding.handlerChain = handlerList
    }
}