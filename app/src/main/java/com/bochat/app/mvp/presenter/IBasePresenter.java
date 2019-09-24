package com.bochat.app.mvp.presenter;

import com.bochat.app.mvp.view.IBaseView;

/**
 * Author:         FJ
 * Version:        1.0
 * CreateDate:     2019/04/08 09:49
 * Description:
 */
public interface IBasePresenter<V extends IBaseView> {
    boolean isActive();
    void onViewAttached(V view);
    void onViewDetached();
    void onViewInactivation();
    void onViewRefresh();
    V getView();
}
