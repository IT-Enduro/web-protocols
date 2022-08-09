package ru.romanow.protocols.soap.config

import org.apache.cxf.Bus
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.romanow.protocols.soap.web.*
import javax.xml.ws.Endpoint

@Configuration
class WebServerConfiguration {

    @Bean
    fun cxfDispatcherServlet(): ServletRegistrationBean<CXFServlet> {
        return ServletRegistrationBean(CXFServlet(), "/*")
    }

    @Bean(name = [Bus.DEFAULT_BUS_ID])
    fun springBus(): SpringBus {
        return SpringBus()
    }

    // region Document Literal
    @Bean
    fun documentLiteralWebService(): WebServiceDocumentLiteral {
        return WebServiceDocumentLiteralImpl()
    }

    @Bean
    fun documentLiteralWebServiceEndpoint(): Endpoint {
        val endpoint = EndpointImpl(springBus(), documentLiteralWebService())
        endpoint.publish("/document-literal")
        return endpoint
    }
    // endregion

    // region Document Literal Wrapped
    @Bean
    fun documentLiteralWrappedWebService(): WebServiceDocumentLiteralWrapped {
        return WebServiceDocumentLiteralWrappedImpl()
    }

    @Bean
    fun documentLiteralWrappedWebServiceEndpoint(): Endpoint {
        val endpoint = EndpointImpl(springBus(), documentLiteralWrappedWebService())
        endpoint.publish("/document-literal-wrapped")
        return endpoint
    }
    // endregion

    // region RPC Literal
    @Bean
    fun rpcLiteralWebService(): WebServiceRpcLiteral {
        return WebServiceRpcLiteralImpl()
    }

    @Bean
    fun rpcLiteralWebServiceEndpoint(): Endpoint {
        val endpoint = EndpointImpl(springBus(), rpcLiteralWebService())
        endpoint.publish("/rpc-literal")
        return endpoint
    }
    // endregion
}