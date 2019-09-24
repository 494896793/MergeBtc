package com.bochat.app.model.net;

import java.io.Serializable;

/**
 * Created by ldl on 2019/4/9.
 */

public class HttpResponseBean implements Serializable {

    /**
     * success : true
     * code : 200
     * msg :
     * data : {"respCode":"00000","respDesc":"请求成功。","failCount":"0","failList":[],"smsId":"67e13e00c56b46f2b7d4d14839144856"}
     */

    private boolean success;
    private int retcode;
    private String msg;
    private Object data;
    private int isUpdate;
    private int code;
    private int version;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getVersion() {
        return version;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
