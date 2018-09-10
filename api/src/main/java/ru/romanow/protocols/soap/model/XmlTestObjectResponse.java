package ru.romanow.protocols.soap.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ronin on 25.09.16
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class XmlTestObjectResponse
        extends TestObjectResponse {
    private static final long serialVersionUID = -202667122908268897L;

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