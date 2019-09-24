package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.OutPromptDataEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface TokenTransferContract {

    interface View extends IBaseView<Presenter> {
        void updateList(String bName, String bId, OutPromptDataEntity entity);
        void setSendAmount(String text);
        void setAddress(String address);
    }

    interface Presenter extends IBasePresenter<View> {
        void onChooseClick();
        void onMaxAmountClick();
        void onEnterClick(String num,String address,String password, int bid);
    }

}