package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.conversation.GroupManageEditNameContract;
import com.bochat.app.common.router.RouterGroupManageEditName;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:44
 * Description :
 */
@Route(path = RouterGroupManageEditName.PATH)
public class GroupManageEditNameActivity extends BaseActivity<GroupManageEditNameContract.Presenter> implements GroupManageEditNameContract.View{

    @Inject
    GroupManageEditNameContract.Presenter presenter;
    
    @BindView(R.id.cv_group_manage_edit_name_input)
    EditText groupNameInput;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupManageEditNameContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_manage_edit_name);
    }

    @OnClick({R.id.cv_group_manage_edit_name_enter_btn, R.id.cv_group_manage_edit_name_clear})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        if(view.getId() == R.id.cv_group_manage_edit_name_clear){
            groupNameInput.setText("");
        } else {
            presenter.onEnterClick(groupNameInput.getText().toString());
        }
    }

    @Override
    public void onRefresh(String groupName) {
        groupNameInput.setText(groupName);
        groupNameInput.setSelection(groupName.length());
    }
}
