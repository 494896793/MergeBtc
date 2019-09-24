package com.bochat.app.common.router;

import com.bochat.app.model.bean.CurrencyDetailEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 10:51
 * Description :
 */

public class RouterTokenTransfer extends AbstractRouter {
    public static final String PATH ="/path/RouterTokenTransfer";
    
    private CurrencyDetailEntity currencyDetailEntity;

    private UserCurrencyEntity userCurrencyEntity;
    
    private String transferAddress;
    
    public RouterTokenTransfer() {
    }

    public RouterTokenTransfer(UserCurrencyEntity userCurrencyEntity) {
        this.userCurrencyEntity = userCurrencyEntity;
    }

    public RouterTokenTransfer(CurrencyDetailEntity currencyDetailEntity) {
        this.currencyDetailEntity = currencyDetailEntity;
    }

    public RouterTokenTransfer(String transferAddress) {
        this.transferAddress = transferAddress;
    }

    public UserCurrencyEntity getUserCurrencyEntity() {
        return userCurrencyEntity;
    }

    public void setUserCurrencyEntity(UserCurrencyEntity userCurrencyEntity) {
        this.userCurrencyEntity = userCurrencyEntity;
    }

    public String getTransferAddress() {
        return transferAddress;
    }

    public void setTransferAddress(String transferAddress) {
        this.transferAddress = transferAddress;
    }

    public CurrencyDetailEntity getCurrencyDetailEntity() {
        return currencyDetailEntity;
    }

    public void setCurrencyDetailEntity(CurrencyDetailEntity currencyDetailEntity) {
        this.currencyDetailEntity = currencyDetailEntity;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
