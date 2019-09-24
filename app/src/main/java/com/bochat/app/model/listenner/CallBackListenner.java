package com.bochat.app.model.listenner;

/**
 * 2019/4/11
 * Author ZZW
 **/
public interface CallBackListenner {

    void success(Object object);
    void failed(int code, String msg);

}
