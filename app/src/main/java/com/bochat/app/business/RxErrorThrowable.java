package com.bochat.app.business;

import com.bochat.app.model.bean.CodeEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/26 14:36
 * Description :
 */

public class RxErrorThrowable extends Throwable{

    private int returnCode;
    private CodeEntity codeEntity;

    public RxErrorThrowable(CodeEntity codeEntity){
        super(codeEntity.getMsg());
        this.returnCode = codeEntity.getRetcode();
        this.codeEntity = codeEntity;
    }

    public CodeEntity getCodeEntity() {
        return codeEntity;
    }

    public int getReturnCode(){
        return returnCode;
    }
}
