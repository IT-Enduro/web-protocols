package ru.romanow.protocols.producer.web

import org.springframework.beans.factory.annotation.Autowired

abstract class BasePingControllerTest : BaseTest() {

    @Autowired
    private lateinit var pingController: PingController

    override fun controller() = pingController
}