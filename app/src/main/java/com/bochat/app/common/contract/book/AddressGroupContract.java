package com.bochat.app.common.contract.book;

import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * 2019/4/19
 * Author LDL
 **/
public interface AddressGroupContract {

    interface View extends IBaseView<AddressGroupContract.Presenter>{
        void onViewRefresh(int page, List<GroupEntity> groupList, boolean isTotalShow);
    }

    interface Presenter extends IBasePresenter<AddressGroupContract.View>{
        void queryMyGroupList(boolean isForce);
        void loadMore(int page);
        void onItemClick(GroupEntity groupEntity);
    }
}
