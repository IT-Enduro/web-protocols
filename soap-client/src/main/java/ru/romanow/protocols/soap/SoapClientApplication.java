package ru.romanow.protocols.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.romanow.protocols.soap.web.WebServiceClient;

/**
 * Created by ronin on 18.09.16
 */
@SpringBootApplication
public class SoapClientApplication
        implements CommandLineRunner {

    @Autowired
    private WebServiceClient documentEncodedWebServiceClient;

    @Autowired
    private WebServiceClient documentLiteralWebServiceClient;

    public static void main(String[] args) {
        SpringApplication.run(SoapClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        documentEncodedWebServiceClient.makeRequest();
//        documentLiteralWebServiceClient.makeRequest();
    }
}
