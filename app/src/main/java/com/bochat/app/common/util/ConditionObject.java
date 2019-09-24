package com.bochat.app.common.util;

import android.os.ConditionVariable;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/22 10:43
 * Description :
 */

public class ConditionObject extends ConditionVariable {
    
    private Object object;
    private boolean isSuccess;

    public ConditionObject(){
    }
    
    public ConditionObject(Object object){
        this.object = object;
    }

    public Object getObject(){
        return object;
    }
    
    public void setObject(Object object){
        this.object = object;
    }
    
    public boolean isHandleSuccess(){
        return isSuccess;
    }
    
    public void setHandleResult(boolean isSuccess){
        this.isSuccess = isSuccess;
    }
}
