package ru.romanow.protocols.model;

import com.google.common.base.MoreObjects;

/**
 * Created by ronin on 16.09.16
 */
public class TestObjectResponse {
    private Integer code;
    private String data;

    public Integer getCode() {
        return code;
    }

    public TestObjectResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getData() {
        return data;
    }

    public TestObjectResponse setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("code", code)
                          .add("data", data)
                          .toString();
    }
}
