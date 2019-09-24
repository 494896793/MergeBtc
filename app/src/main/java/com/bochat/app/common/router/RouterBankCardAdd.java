package com.bochat.app.common.router;

import com.bochat.app.model.bean.BankCard;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 14:04
 * Description :
 */

public class RouterBankCardAdd extends AbstractRouter {
    public static final String PATH ="/path/RouterBankCardAdd";
    
    private BankCard bankCard;

    public RouterBankCardAdd() {
    }

    public RouterBankCardAdd(BankCard bankCard) {
        this.bankCard = bankCard;
    }

    public BankCard getBankCard() {
        return bankCard;
    }

    public void setBankCard(BankCard bankCard) {
        this.bankCard = bankCard;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
