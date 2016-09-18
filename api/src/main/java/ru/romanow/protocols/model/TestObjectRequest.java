package ru.romanow.protocols.model;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * Created by ronin on 16.09.16
 */
public class TestObjectRequest
        implements Serializable {
    private static final long serialVersionUID = -2623091425221235012L;

    private Integer id;
    private String searchString;

    public Integer getId() {
        return id;
    }

    public TestObjectRequest setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getSearchString() {
        return searchString;
    }

    public TestObjectRequest setSearchString(String searchString) {
        this.searchString = searchString;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("searchString", searchString)
                          .toString();
    }
}
