package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.app.view.OperationDialog;
import com.bochat.app.common.contract.conversation.GroupMemberManageContract;
import com.bochat.app.common.router.RouterGroupMemberManage;
import com.bochat.app.common.util.SharePreferenceUtil;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:44
 * Description :
 */
@Route(path = RouterGroupMemberManage.PATH)
public class GroupMemberManageActivity extends BaseActivity<GroupMemberManageContract.Presenter> implements GroupMemberManageContract.View{
    @BindView(R.id.cv_group_member_manage_forbidden)
    BoChatItemView boChatItemView;

    @BindView(R.id.cv_group_member_manage_enter_btn)
    Button removeGroupMember;


    @Inject
    GroupMemberManageContract.Presenter presenter;

    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupMemberManageContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_member_manage);
    }

    @Override
    protected void initWidget() {


        boChatItemView.getSwitchBtn().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onForbiddenSwitch(isChecked);

            }
        });

        removeGroupMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new OperationDialog.Builder(getViewContext()).setContent("确定移除群聊吗？")
                        .setOnClickItemListener(new OperationDialog.OnClickItemListener() {
                            @Override
                            public void onEnter(OperationDialog dialog, View v) {
                              presenter.onEnterBtnClick();

                            }

                            @Override
                            public void onCancel(OperationDialog dialog, View v) {
                            }
                        }).build().show();

            }
        });
    }

    @Override
    public void updateSwitchState(boolean isChecked) {
        if (isChecked){
            boChatItemView.getSwitchBtn().setChecked(true);
        }else {
            boChatItemView.getSwitchBtn().setChecked(false);
        }
    }
}
