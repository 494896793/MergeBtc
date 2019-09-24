package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.TokenEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/10 11:43
 * Description :
 */

public interface TokenSelectContract {
    interface View extends IBaseView<TokenSelectContract.Presenter> {
        void updateList(List<TokenEntity> data);
    }

    interface Presenter extends IBasePresenter<TokenSelectContract.View> {
        void onItemClick(TokenEntity data);
    }
}
