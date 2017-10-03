package ru.romanow.protocols.grpc;

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

    @Autowired
    private TestGrpcClient testGrpcClient;

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
