
package ru.romanow.protocols.soap.generated.wrapped.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TestObjectRequest" type="{http://web.soap.protocols.romanow.ru/}testObjectRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processRequest", propOrder = {
    "testObjectRequest"
})
public class ProcessRequest {

    @XmlElement(name = "TestObjectRequest")
    protected TestObjectRequest testObjectRequest;

    /**
     * Gets the value of the testObjectRequest property.
     * 
     * @return
     *     possible object is
     *     {@link TestObjectRequest }
     *     
     */
    public TestObjectRequest getTestObjectRequest() {
        return testObjectRequest;
    }

    /**
     * Sets the value of the testObjectRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestObjectRequest }
     *     
     */
    public void setTestObjectRequest(TestObjectRequest value) {
        this.testObjectRequest = value;
    }

}
