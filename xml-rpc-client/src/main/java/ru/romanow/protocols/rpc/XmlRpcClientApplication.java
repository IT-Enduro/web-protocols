package ru.romanow.protocols.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.romanow.protocols.rpc.service.RpcClient;

@SpringBootApplication
public class XmlRpcClientApplication
        implements CommandLineRunner {

    @Autowired
    private RpcClient client;

    public static void main(String[] args) {
        SpringApplication.run(XmlRpcClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        client.testRequest();
        client.testSum();
    }
}
