package ru.romanow.protocols.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.romanow.protocols.grpc.service.TestGrpcClient;

/**
 * Created by romanow on 03.10.17.
 */
@SpringBootApplication
public class GrpcClientApplication
        implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClientApplication.class);

    @Autowired
    private TestGrpcClient testGrpcClient;

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("{}", testGrpcClient.testClient());
    }
}
