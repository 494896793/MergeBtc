package com.bochat.app.business.main.conversation;

import com.bochat.app.common.contract.conversation.GroupChatContract;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/16 11:44
 * Description :
 */

public class GroupChatPresenter extends BasePresenter<GroupChatContract.View> implements GroupChatContract.Presenter{
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
