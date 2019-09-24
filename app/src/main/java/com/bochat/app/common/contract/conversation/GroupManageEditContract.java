package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:11
 * Description :
 */

public interface GroupManageEditContract {
    interface View extends IBaseView<GroupManageEditContract.Presenter> {
        public void onRefresh(GroupEntity groupEntity);
        public void updateNameText(String text);
    }

    interface Presenter extends IBasePresenter<GroupManageEditContract.View> {
        public void onEditGroupNameClick();
        public void onEnterClick(GroupEntity entity);
    }
}
