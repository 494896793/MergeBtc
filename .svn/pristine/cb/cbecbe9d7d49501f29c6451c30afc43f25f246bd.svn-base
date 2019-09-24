package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.DynamicFlashEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * create by guoying ${Date} and ${Month}
 */
public interface DynamicFlashDetialContract {
    interface View extends IBaseView<Presenter>{
        void onUpdateView(DynamicFlashEntity e);
        void showShareDialog(String shareUrl);
        void onUpdateQrCode(String loadUrl);
    }
    interface Presenter extends IBasePresenter<View>{
        void loadFlashDetail();
        void onShareClick();
        void setBackData();

    }
}
