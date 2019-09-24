package com.bochat.app.common.model;

import com.bochat.app.model.bean.CodeEntity;

/**
 * 2019/4/10
 * Author LDL
 **/
public interface ISendMessageModel {

    public CodeEntity sendMessage(String phone, int type);

    public CodeEntity checkMessage(String phone, String type, String code);




}
