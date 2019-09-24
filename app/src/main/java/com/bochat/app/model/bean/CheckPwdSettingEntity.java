package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/26
 * Author LDL
 **/
public class CheckPwdSettingEntity extends CodeEntity implements Serializable {

    private String password;            //登录密码      1：未设置 2：已设置
    private String tradePassword;       //交易密码      1：未设置 2：已设置

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    @Override
    public String toString() {
        return "CheckPwdSettingEntity{" +
                "password='" + password + '\'' +
                ", tradePassword='" + tradePassword + '\'' +
                '}';
    }
}
