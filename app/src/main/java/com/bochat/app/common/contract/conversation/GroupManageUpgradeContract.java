package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.GroupLevelEntity;
import com.bochat.app.model.bean.GroupLevelListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:11
 * Description :
 */

public interface GroupManageUpgradeContract {
    
    interface View extends IBaseView<GroupManageUpgradeContract.Presenter> {
        public void updateList(GroupLevelListEntity list);
    }

    interface Presenter extends IBasePresenter<GroupManageUpgradeContract.View> {
        public void onItemClick(GroupLevelEntity level);
        public void onEnterPay(GroupLevelEntity level, String password);
    }
}
