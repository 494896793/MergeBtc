package com.bochat.app.common.router;

import com.bochat.app.model.bean.AmountEntity;
import com.bochat.app.model.bean.BankCard;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 17:10
 * Description :
 */

public class RouterPropertyCashOut extends AbstractRouter {
    public static final String PATH ="/path/RouterPropertyCashOut";
    
    private AmountEntity amountEntity;
    private BankCard bankCard;

    public RouterPropertyCashOut(BankCard bankCard) {
        this.bankCard = bankCard;
    }

    public RouterPropertyCashOut() {

    }


    public RouterPropertyCashOut(AmountEntity amountEntity) {
        this.amountEntity = amountEntity;
    }

    public AmountEntity getAmountEntity() {
        return amountEntity;
    }

    public void setAmountEntity(AmountEntity amountEntity) {
        this.amountEntity = amountEntity;
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
