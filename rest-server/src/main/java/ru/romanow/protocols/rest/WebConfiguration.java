package ru.romanow.protocols.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by ronin on 20.09.16
 */
@Configuration
@EnableWebMvc
public class WebConfiguration
        extends WebMvcConfigurationSupport {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api-doc")
                .allowedMethods(HttpMethod.GET.name())
                .allowedOrigins("*");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8)
                  .mediaType("json", MediaType.APPLICATION_JSON_UTF8)
                  .mediaType("xml", MediaType.APPLICATION_XML);
    }
}
