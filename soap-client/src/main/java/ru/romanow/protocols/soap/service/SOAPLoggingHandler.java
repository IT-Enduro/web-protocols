package ru.romanow.protocols.soap.service;

import lombok.SneakyThrows;
import org.slf4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

public class SOAPLoggingHandler
        implements SOAPHandler<SOAPMessageContext> {
    private static final Logger logger = getLogger(SOAPLoggingHandler.class);

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext soapMessageContext) {
        logToSystemOut(soapMessageContext);
        return true;
    }

    public boolean handleFault(SOAPMessageContext soapMessageContext) {
        logToSystemOut(soapMessageContext);
        return true;
    }

    public void close(MessageContext messageContext) {
    }

    @SneakyThrows
    private void logToSystemOut(SOAPMessageContext soapMessageContext) {
        boolean outboundProperty = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        final String direction = outboundProperty ? "Outbound message" : "Inbound message";

        final SOAPMessage message = soapMessageContext.getMessage();
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        message.writeTo(stream);

        logger.info("{}:\n{}", direction, prettyFormat(stream.toString()));
    }

    @SneakyThrows
    public String prettyFormat(String xml) {
        Source xmlInput = new StreamSource(new StringReader(xml));
        StringWriter stringWriter = new StringWriter();
        StreamResult xmlOutput = new StreamResult(stringWriter);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 2);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(xmlInput, xmlOutput);
        return xmlOutput.getWriter().toString();
    }
}