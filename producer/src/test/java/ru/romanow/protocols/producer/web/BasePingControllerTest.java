package ru.romanow.protocols.producer.web;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BasePingControllerTest
        extends BaseTest {

    @Autowired
    private PingController pingController;

    @Override
    protected Object controller() {
        return pingController;
    }
}
