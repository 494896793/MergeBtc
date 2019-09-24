package com.bochat.app.business.main.conversation;

import com.bochat.app.common.contract.conversation.FriendManageNoteContract;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 09:06
 * Description :
 */

public class FriendManageNotePresenter extends BasePresenter<FriendManageNoteContract.View> implements FriendManageNoteContract.Presenter{
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onEnterBtnClick(String note) {
        getView().finish();
    }
}
