package ru.romanow.protocols.soap;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.romanow.protocols.soap.generated.model.TestObjectRequest;
import ru.romanow.protocols.soap.web.WebServiceClient;

/**
 * Created by ronin on 18.09.16
 */
@SpringBootApplication
public class SoapClientApplication
        implements CommandLineRunner {

    @Autowired
    private WebServiceClient webServiceClient;

    public static void main(String[] args) {
        SpringApplication.run(SoapClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        TestObjectRequest request = new TestObjectRequest();
        request.setId(RandomUtils.nextInt(0, 1000));
        request.setSearchString(RandomStringUtils.randomAlphabetic(10));
        webServiceClient.makeRequest(request);
    }
}
