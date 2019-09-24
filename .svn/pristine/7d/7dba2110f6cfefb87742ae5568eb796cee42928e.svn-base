package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.common.contract.conversation.GroupManageContract;
import com.bochat.app.common.router.RouterGroupManage;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:44
 * Description :
 */

@Route(path = RouterGroupManage.PATH)
public class GroupManageActivity extends BaseActivity<GroupManageContract.Presenter> implements GroupManageContract.View{

    @Inject
    GroupManageContract.Presenter presenter;
    
    @BindView(R.id.cv_group_manage_upgrade)
    BoChatItemView upgrade;
    @BindView(R.id.cv_group_manage_manager)
    BoChatItemView manager;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupManageContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_manage);
    }
    
    @Override
    public void updateGroupInfo(GroupEntity groupEntity, boolean isOwner) {
        upgrade.getContentView().setText(groupEntity.getLevel_num()+ "人");
        manager.setVisibility(isOwner ? View.VISIBLE : View.GONE);
        manager.getContentView().setText(groupEntity.getManagers().size()+"人");
    }

    @Override
    public void updateGrouManagerNum(int num) {
        manager.getContentView().setText(num+"人");
    }

    @OnClick({R.id.cv_group_manage_edit, R.id.cv_group_manage_forbidden, R.id.cv_group_manage_upgrade,
            R.id.cv_group_manage_manager, R.id.cv_group_manage_join_filter})
    @Override
    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_group_manage_edit:
                presenter.onGroupEditClick();
            break;
            case R.id.cv_group_manage_forbidden:
                presenter.onGroupForbiddenClick();
            break;
            case R.id.cv_group_manage_upgrade:
                presenter.onGroupUpgradeClick();  
            break;
            case R.id.cv_group_manage_manager: 
                presenter.onGroupManagerSettingClick();
                break;
            case R.id.cv_group_manage_join_filter:
                presenter.onGroupJoinFilterClick();
                break;
            default:
                break;
        }
    }
}
