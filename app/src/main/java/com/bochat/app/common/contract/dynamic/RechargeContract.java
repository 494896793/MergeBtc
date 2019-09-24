package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.RechargeVipEntity;
import com.bochat.app.model.bean.RechargeVipSuccessEntity;
import com.bochat.app.model.bean.VipStatuEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.math.BigInteger;

/**
 * 2019/7/16
 * Author LDL
 **/
public interface RechargeContract {


    interface View extends IBaseView<RechargeContract.Presenter>{
        void setRechargeAdapter(RechargeVipEntity entity);
        void paySuccess(RechargeVipSuccessEntity entity);
    }

    interface Presenter extends IBasePresenter<RechargeContract.View>{
        void query();
        void payVip(String id, String tradePass);
    }

}
