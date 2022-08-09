package ru.romanow.protocols.producer.web

import org.springframework.beans.factory.annotation.Autowired

abstract class BaseOperationControllerTest : BaseTest() {

    @Autowired
    private lateinit var operationController: OperationController

    override fun controller() = operationController
}