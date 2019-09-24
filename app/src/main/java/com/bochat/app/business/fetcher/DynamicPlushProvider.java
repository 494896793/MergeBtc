package com.bochat.app.business.fetcher;

import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.model.bean.DynamicPlushEntity;

/**
 * 2019/7/10
 * Author LDL
 **/
public class DynamicPlushProvider {

    private IDynamicModel dynamicModel;
    private DynamicPlushEntity dynamicPlushEntity;

    public DynamicPlushProvider(IDynamicModel dynamicModel){
        this.dynamicModel=dynamicModel;
    }

    public synchronized DynamicPlushEntity getDynamicPlush(){
       if(dynamicPlushEntity==null){
           return dynamicPlushEntity= dynamicModel.push();
       }
       return dynamicPlushEntity;
    }

}
