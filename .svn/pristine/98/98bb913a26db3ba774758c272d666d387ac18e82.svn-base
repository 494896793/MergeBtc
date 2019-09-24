package com.bochat.app.app.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.conversation.GroupManageManagerContract;
import com.bochat.app.common.router.RouterGroupMemberManager;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.mvp.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:44
 * Description :
 */
@Route(path = RouterGroupMemberManager.PATH)
public class GroupManageManagerActivity extends BaseActivity<GroupManageManagerContract.Presenter> implements GroupManageManagerContract.View{

    @Inject
    GroupManageManagerContract.Presenter presenter;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupManageManagerContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_manage_manager);
    }
    
    @Override
    public void updateList(List<GroupMemberEntity> list) {
        
    }
}
