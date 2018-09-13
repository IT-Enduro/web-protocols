package ru.romanow.protocols.consumer.web;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseOperationControllerTest
        extends BaseTest {

    @Autowired
    private OperationController operationController;

    @Override
    protected Object controller() {
        return operationController;
    }
}
