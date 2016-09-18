
package ru.romanow.protocols.soap.generated.model;

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
 * &lt;complexType name="processRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TestObjectRequest" type="{http://web.soap.protocols.romanow.ru/}testObjectRequest" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
