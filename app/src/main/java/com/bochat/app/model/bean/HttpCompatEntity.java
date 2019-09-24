package com.bochat.app.model.bean;

import java.io.Serializable;

public class HttpCompatEntity<E> extends CodeEntity implements Serializable {

    public E data;

    public void setData(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }
}
