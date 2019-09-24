package com.bochat.app.app.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.conversation.GroupMemberManageNoteContract;
import com.bochat.app.common.router.RouterGroupMemberManageNote;
import com.bochat.app.mvp.view.BaseActivity;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:44
 * Description :
 */
@Route(path = RouterGroupMemberManageNote.PATH)
public class GroupMemberManageNoteActivity extends BaseActivity<GroupMemberManageNoteContract.Presenter> implements GroupMemberManageNoteContract.View{

    GroupMemberManageNoteContract.Presenter presenter;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupMemberManageNoteContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_member_manage_note);
    }

    @Override
    protected void initWidget() {

    }
}
