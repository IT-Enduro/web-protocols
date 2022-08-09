package ru.romanow.protocols.soap.service

import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.StringReader
import java.io.StringWriter
import javax.xml.namespace.QName
import javax.xml.soap.SOAPMessage
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import javax.xml.ws.handler.MessageContext
import javax.xml.ws.handler.soap.SOAPHandler
import javax.xml.ws.handler.soap.SOAPMessageContext

class SOAPLoggingHandler : SOAPHandler<SOAPMessageContext> {
    private val logger = LoggerFactory.getLogger(SOAPLoggingHandler::class.java)

    override fun handleMessage(soapMessageContext: SOAPMessageContext): Boolean {
        logToSystemOut(soapMessageContext)
        return true
    }

    override fun handleFault(soapMessageContext: SOAPMessageContext): Boolean {
        logToSystemOut(soapMessageContext)
        return true
    }

    override fun close(messageContext: MessageContext) {}
    private fun logToSystemOut(soapMessageContext: SOAPMessageContext) {
        val outboundProperty = soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY) as Boolean
        val direction = if (outboundProperty) "Outbound message" else "Inbound message"
        val message: SOAPMessage = soapMessageContext.getMessage()
        val stream = ByteArrayOutputStream()
        message.writeTo(stream)
        logger.info("{}:\n{}", direction, prettyFormat(stream.toString()))
    }

    private fun prettyFormat(xml: String): String {
        val xmlInput: Source = StreamSource(StringReader(xml))
        val stringWriter = StringWriter()
        val xmlOutput = StreamResult(stringWriter)
        val transformerFactory = TransformerFactory.newInstance()
        transformerFactory.setAttribute("indent-number", 2)
        val transformer = transformerFactory.newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.transform(xmlInput, xmlOutput)
        return xmlOutput.writer.toString()
    }

    override fun getHeaders(): MutableSet<QName>? = null
}