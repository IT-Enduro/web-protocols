package ru.romanow.protocols.soap.config

import jakarta.xml.ws.Endpoint
import org.apache.cxf.Bus
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.romanow.protocols.soap.web.ServerWebService


@Configuration
class WebServerConfiguration {

    @Bean
    fun cxfDispatcherServlet(): ServletRegistrationBean<CXFServlet> {
        return ServletRegistrationBean(CXFServlet(), "/ws/*")
    }

    @Bean(name = [Bus.DEFAULT_BUS_ID])
    fun springBus(): SpringBus {
        return SpringBus()
    }

    @Bean
    fun documentLiteralWebServiceEndpoint(service: ServerWebService): Endpoint {
        val endpoint = EndpointImpl(springBus(), service)
        endpoint.publish("/servers")
        return endpoint
    }

}
