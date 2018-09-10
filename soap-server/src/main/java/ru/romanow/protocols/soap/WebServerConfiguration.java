package ru.romanow.protocols.soap;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romanow.protocols.soap.web.*;

import javax.xml.ws.Endpoint;

/**
 * Created by ronin on 16.09.16
 */
@Configuration
public class WebServerConfiguration {

    @Bean
    public ServletRegistrationBean<CXFServlet> dispatcherServlet() {
        return new ServletRegistrationBean<>(new CXFServlet(), "/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    // region Document Literal
    @Bean
    public WebServiceDocumentLiteral documentLiteralWebService() {
        return new WebServiceDocumentLiteralImpl();
    }

    @Bean
    public Endpoint documentLiteralWebServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), documentLiteralWebService());
        endpoint.publish("/document-literal");
        return endpoint;
    }
    // endregion

    // region Document Literal Wrapped
    @Bean
    public WebServiceDocumentLiteralWrapped documentLiteralWrappedWebService() {
        return new WebServiceDocumentLiteralWrappedImpl();
    }

    @Bean
    public Endpoint documentLiteralWrappedWebServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), documentLiteralWrappedWebService());
        endpoint.publish("/document-literal-wrapped");
        return endpoint;
    }
    // endregion

    // region RPC Literal
    @Bean
    public WebServiceRpcLiteral rpcLiteralWebService() {
        return new WebServiceRpcLiteralImpl();
    }

    @Bean
    public Endpoint rpcLiteralWebServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), rpcLiteralWebService());
        endpoint.publish("/rpc-literal");
        return endpoint;
    }
    // endregion
}
