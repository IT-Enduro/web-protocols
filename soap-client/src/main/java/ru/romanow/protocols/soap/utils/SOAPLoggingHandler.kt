package ru.romanow.protocols.soap.utils

import jakarta.xml.ws.handler.MessageContext
import jakarta.xml.ws.handler.soap.SOAPHandler
import jakarta.xml.ws.handler.soap.SOAPMessageContext
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.StringReader
import java.io.StringWriter
import javax.xml.namespace.QName
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

class SOAPLoggingHandler : SOAPHandler<SOAPMessageContext> {
    private val logger = LoggerFactory.getLogger(SOAPLoggingHandler::class.java)

    override fun handleMessage(soapMessageContext: SOAPMessageContext): Boolean {
        val stream = ByteArrayOutputStream()
        soapMessageContext.message.writeTo(stream)

        val outboundProperty = soapMessageContext[MessageContext.MESSAGE_OUTBOUND_PROPERTY] as Boolean
        val direction = if (outboundProperty) "Outgoing" else "Incoming"

        logger.info("$direction message:\n{}", prettyPrint(stream.toString()))

        return true
    }

    override fun handleFault(soapMessageContext: SOAPMessageContext): Boolean {
        val stream = ByteArrayOutputStream()
        soapMessageContext.message.writeTo(stream)

        logger.info("Outgoing message:\n{}", prettyPrint(stream.toString()))

        return true
    }

    private fun prettyPrint(xml: String): String {
        val xmlInput = StreamSource(StringReader(xml))
        val xmlOutput = StreamResult(StringWriter())

        val transformerFactory = TransformerFactory.newInstance()
        transformerFactory.setAttribute("indent-number", 2)
        val transformer = transformerFactory.newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.transform(xmlInput, xmlOutput)

        return xmlOutput.writer.toString()
    }

    override fun close(messageContext: MessageContext) {}

    override fun getHeaders(): MutableSet<QName>? = null
}