package com.bochat.app.app.util;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/30 17:54
 * Description :
 */

public class SelectableObject<T> {

    private T t;
    private boolean isSelect;
    
    public SelectableObject(T object) {
        t = object;
    }

    public T getObject() {
        return t;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
