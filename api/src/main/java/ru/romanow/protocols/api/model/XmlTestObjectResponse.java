package ru.romanow.protocols.api.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class XmlTestObjectResponse
        extends TestObjectResponse {

    @Override
    @XmlElement
    public Integer getCode() {
        return super.getCode();
    }

    @Override
    public XmlTestObjectResponse setCode(Integer code) {
        super.setCode(code);
        return this;
    }

    @Override
    @XmlElement
    public String getData() {
        return super.getData();
    }

    @Override
    public XmlTestObjectResponse setData(String data) {
        super.setData(data);
        return this;
    }
}
