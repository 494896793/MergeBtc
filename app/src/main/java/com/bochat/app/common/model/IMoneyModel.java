package com.bochat.app.common.model;

import com.bochat.app.model.bean.AmountEntity;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.MoneyClassEntity;
import com.bochat.app.model.bean.MoneyGoodsListEntity;
import com.bochat.app.model.bean.RechargeEntity;

/**
 * 2019/5/4
 * Author LDL
 **/
public interface IMoneyModel {

    AmountEntity getAmount();                                               //已通过

    CodeEntity userWithdraw(int bankId, String money, String tradePwd);         //已通过

    MoneyClassEntity getClasses(int type);          //已通过

    RechargeEntity userRecharge(int payType, String money);        //已通过

    MoneyGoodsListEntity getHandpick();         //已通过


}
