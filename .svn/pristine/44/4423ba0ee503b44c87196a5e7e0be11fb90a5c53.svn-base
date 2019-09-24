package com.bochat.app.model.bean;


import android.text.TextUtils;

import java.io.Serializable;

/**
 *
 * 2019-4-10
 * Author LDL
 * 此实体为通用实体，针对只判断状态码逻辑
 *
 **/
public class CodeEntity  implements Serializable {

    private String msg;
    private String code;
    private int retcode;
    private int count;          //0-未领取  1-已领完  2-失效

    public CodeEntity() {
    }

    public CodeEntity(String msg, String code, int retcode, int count) {
        this.msg = msg;
        this.code = code;
        this.retcode = retcode;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCode() {
        return TextUtils.isEmpty(code) ? 0 :Integer.valueOf(code);
    }
    
    public String getStringCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = String.valueOf(code);
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
