package ru.romanow.protocols.soap;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.romanow.protocols.soap.web.WebServiceClient;

import java.util.List;

@AllArgsConstructor
public class SoapClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoapClientApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public ApplicationRunner runner(List<WebServiceClient> clients) {
        return (args) -> clients.forEach(WebServiceClient::makeRequest);
    }
}
