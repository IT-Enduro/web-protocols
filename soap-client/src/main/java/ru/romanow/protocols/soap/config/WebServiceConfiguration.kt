package ru.romanow.protocols.soap.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.romanow.protocols.soap.generated.literal.model.WebServiceDocumentLiteralImplService
import ru.romanow.protocols.soap.generated.rpc.model.WebServiceRpcLiteralImplService
import ru.romanow.protocols.soap.generated.wrapped.model.WebServiceDocumentLiteralWrappedImplService
import ru.romanow.protocols.soap.service.SOAPLoggingHandler
import ru.romanow.protocols.soap.web.DocumentLiteralWebServiceClient
import ru.romanow.protocols.soap.web.DocumentWrappedWebServiceClient
import ru.romanow.protocols.soap.web.RpcLiteralWebServiceClient
import ru.romanow.protocols.soap.web.WebServiceClient
import javax.xml.ws.BindingProvider

@Configuration
class WebServiceConfiguration {

    @Bean
    fun documentEncodedWebServiceClient(): WebServiceClient {
        val service = WebServiceDocumentLiteralWrappedImplService()
        val port = service.webServiceDocumentLiteralWrappedImplPort
        configureLogging(port as BindingProvider)
        return DocumentWrappedWebServiceClient(port)
    }

    @Bean
    fun documentLiteralWebServiceClient(): WebServiceClient {
        val service = WebServiceDocumentLiteralImplService()
        val port = service.webServiceDocumentLiteralImplPort
        configureLogging(port as BindingProvider)
        return DocumentLiteralWebServiceClient(port)
    }

    @Bean
    fun rpcLiteralWebServiceClient(): WebServiceClient {
        val service = WebServiceRpcLiteralImplService()
        val port = service.webServiceRpcLiteralImplPort
        configureLogging(port as BindingProvider)
        return RpcLiteralWebServiceClient(port)
    }

    private fun configureLogging(port: BindingProvider) {
        val binding = port.binding
        val handlerList = binding.handlerChain
        handlerList.add(SOAPLoggingHandler())
        binding.handlerChain = handlerList
    }
}