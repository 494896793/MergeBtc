package com.bochat.app.business.main.conversation;

import com.bochat.app.common.contract.conversation.GroupMemberManageNoteContract;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/26 10:52
 * Description :
 */

public class GroupMemberManageNotePresenter extends BasePresenter<GroupMemberManageNoteContract.View> implements GroupMemberManageNoteContract.Presenter{
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onEnterBtnClick(String note) {
        
    }
}
