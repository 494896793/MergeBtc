package com.bochat.app.common.router;

import com.bochat.app.model.bean.CurrencyDetailEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 13:44
 * Description :
 */

public class RouterTokenTransferReceive extends AbstractRouter {
    public static final String PATH ="/path/RouterTokenTransferReceive";

    private CurrencyDetailEntity currencyDetailEntity;
    private UserCurrencyEntity userCurrencyEntity;
    
    public RouterTokenTransferReceive() {
    }

    public RouterTokenTransferReceive(CurrencyDetailEntity currencyDetailEntity) {
        this.currencyDetailEntity = currencyDetailEntity;
    }

    public RouterTokenTransferReceive(UserCurrencyEntity userCurrencyEntity) {
        this.userCurrencyEntity = userCurrencyEntity;
    }

    public CurrencyDetailEntity getCurrencyDetailEntity() {
        return currencyDetailEntity;
    }

    public void setCurrencyDetailEntity(CurrencyDetailEntity currencyDetailEntity) {
        this.currencyDetailEntity = currencyDetailEntity;
    }

    public UserCurrencyEntity getUserCurrencyEntity() {
        return userCurrencyEntity;
    }

    public void setUserCurrencyEntity(UserCurrencyEntity userCurrencyEntity) {
        this.userCurrencyEntity = userCurrencyEntity;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
