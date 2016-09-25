
package ru.romanow.protocols.soap.generated.literal.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.romanow.protocols.soap.generated.literal.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TestObjectRequest_QNAME = new QName("http://web.soap.protocols.romanow.ru/", "TestObjectRequest");
    private final static QName _TestObjectResponse_QNAME = new QName("http://web.soap.protocols.romanow.ru/", "TestObjectResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.romanow.protocols.soap.generated.literal.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TestObjectRequest }
     * 
     */
    public TestObjectRequest createTestObjectRequest() {
        return new TestObjectRequest();
    }

    /**
     * Create an instance of {@link TestObjectResponse }
     * 
     */
    public TestObjectResponse createTestObjectResponse() {
        return new TestObjectResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestObjectRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.protocols.romanow.ru/", name = "TestObjectRequest")
    public JAXBElement<TestObjectRequest> createTestObjectRequest(TestObjectRequest value) {
        return new JAXBElement<TestObjectRequest>(_TestObjectRequest_QNAME, TestObjectRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.protocols.romanow.ru/", name = "TestObjectResponse")
    public JAXBElement<TestObjectResponse> createTestObjectResponse(TestObjectResponse value) {
        return new JAXBElement<TestObjectResponse>(_TestObjectResponse_QNAME, TestObjectResponse.class, null, value);
    }

}
