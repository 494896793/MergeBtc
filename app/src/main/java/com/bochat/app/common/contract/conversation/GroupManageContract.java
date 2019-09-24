package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:11
 * Description :
 */

public interface GroupManageContract {
    interface View extends IBaseView<GroupManageContract.Presenter> {
        public void updateGroupInfo(GroupEntity groupEntity, boolean isOwner);
        public void updateGrouManagerNum(int num);
    }

    interface Presenter extends IBasePresenter<GroupManageContract.View> {
        public void onGroupEditClick();
        public void onGroupForbiddenClick();
        public void onGroupUpgradeClick();
        public void onGroupManagerSettingClick();
        public void onGroupJoinFilterClick();
    }
}