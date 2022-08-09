package ru.romanow.protocols.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.romanow.protocols.grpc.service.TestGrpcClient;

@SpringBootApplication
public class GrpcClientApplication {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public ApplicationRunner runner(TestGrpcClient testGrpcClient) {
        return args -> {
            logger.info("{}", testGrpcClient.testClient());
        };
    }
}
