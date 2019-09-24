package com.bochat.app.model.bean;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/17 11:52
 * Description :
 */

public class CodeEntityNoneCount {
    private String msg;
    private int code;
    private int retcode;

    public CodeEntityNoneCount() {
    }

    public CodeEntityNoneCount(String msg, int code, int retcode) {
        this.msg = msg;
        this.code = code;
        this.retcode = retcode;
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

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }
}
