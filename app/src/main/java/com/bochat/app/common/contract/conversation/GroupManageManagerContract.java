package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:11
 * Description :
 */

public interface GroupManageManagerContract {
    
    interface View extends IBaseView<GroupManageManagerContract.Presenter> {
        void updateList(List<GroupMemberEntity> list);
    }

    interface Presenter extends IBasePresenter<GroupManageManagerContract.View> {
        void addManager(GroupMemberEntity member);
        void deleteManager(GroupMemberEntity manager);
    }
}
