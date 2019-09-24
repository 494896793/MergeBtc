package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * Created by ldl on 2019/4/9.
 */

public enum ResponseCode implements Serializable {
    SUCCESS_CODE(200,"success"),
    ERROR_CODE(1000,"error"),

    HTTP_CLIENT_EXCEPTION(9000,"网络响应异常"),
    RESPONSE_BODY_NULL(9001,"程序员正在拼命修复中~");//body为null

    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
