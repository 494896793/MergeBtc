package com.bochat.app.common.contract.conversation;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:11
 * Description :
 */

public interface GroupMemberManageContract {
    interface View extends IBaseView<GroupMemberManageContract.Presenter> {
        public void updateSwitchState(boolean isChecked);

    }

    interface Presenter extends IBasePresenter<GroupMemberManageContract.View> {
        public void onForbiddenSwitch(boolean isForbidden);
        public void onEnterBtnClick();
    }
}
