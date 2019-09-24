package com.bochat.app.common.bean;

import java.io.Serializable;
import java.util.HashMap;

import io.rong.imlib.model.ReadReceiptInfo;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/21 14:50
 * Description :
 */

public class ReadReceiptInfoCopy implements Serializable {

    private boolean isReadReceiptMessage;
    private boolean hasRespond;
    private HashMap<String, Long> respondUserIdList = new HashMap();
    
    public ReadReceiptInfoCopy(ReadReceiptInfo readReceiptInfo) {
        if(readReceiptInfo != null){
            setReadReceiptMessage(readReceiptInfo.isReadReceiptMessage());
            setRespondUserIdList(readReceiptInfo.getRespondUserIdList());
            setHasRespond(readReceiptInfo.hasRespond());
        }
    }

    public boolean isReadReceiptMessage() {
        return isReadReceiptMessage;
    }

    public void setReadReceiptMessage(boolean readReceiptMessage) {
        isReadReceiptMessage = readReceiptMessage;
    }

    public boolean isHasRespond() {
        return hasRespond;
    }

    public void setHasRespond(boolean hasRespond) {
        this.hasRespond = hasRespond;
    }

    public HashMap<String, Long> getRespondUserIdList() {
        return respondUserIdList;
    }

    public void setRespondUserIdList(HashMap<String, Long> respondUserIdList) {
        this.respondUserIdList = respondUserIdList;
    }
}
