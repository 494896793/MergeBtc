package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.PrivilegeListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

public interface PrivilegeContract {

    interface View extends IBaseView<Presenter> {
        void obtainPrivileges(PrivilegeListEntity entity);
    }

    interface Presenter extends IBasePresenter<View> {
        void listPrivileges(int currentPage, int pageSize, boolean isNoData);
    }
}