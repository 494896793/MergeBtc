package com.bochat.app.common.contract.conversation;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:11
 * Description :
 */

public interface GroupMemberManageNoteContract {
    interface View extends IBaseView<GroupMemberManageNoteContract.Presenter> {
    }

    interface Presenter extends IBasePresenter<GroupMemberManageNoteContract.View> {
        public void onEnterBtnClick(String note);
    }
}
