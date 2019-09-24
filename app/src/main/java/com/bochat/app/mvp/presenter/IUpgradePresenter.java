package com.bochat.app.mvp.presenter;

import com.bochat.app.mvp.view.IBaseView;

public interface IUpgradePresenter<V extends IBaseView> extends IBasePresenter<V> {
    void upgradeApplication();
}
