package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.app.view.OperationDialog;
import com.bochat.app.common.contract.conversation.FriendManageContract;
import com.bochat.app.common.router.RouterFriendManage;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/29 10:09
 * Description :
 */

@Route(path = RouterFriendManage.PATH)
public class FriendManageActivity extends BaseActivity<FriendManageContract.Presenter> implements FriendManageContract.View {

    @Inject
    FriendManageContract.Presenter presenter;
    
    @BindView(R.id.cv_friend_manage_notification_switch)
    BoChatItemView notificationItem;
    @BindView(R.id.cv_friend_manage_conversation_top_switch)
    BoChatItemView topItem;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected FriendManageContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_friend_manage);
    }

    @OnClick({R.id.cv_friend_manage_enter_btn,R.id.cv_friend_manage_history, R.id.cv_friend_manage_history_clear})
    @Override
    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_friend_manage_history:
                presenter.onSearchHistoryClick();
            break;
            case R.id.cv_friend_manage_history_clear:
                new OperationDialog.Builder(this).setContent("确定删除聊天记录吗？")
                        .setOnClickItemListener(new OperationDialog.OnClickItemListener() {
                            @Override
                            public void onEnter(OperationDialog dialog, View v) {
                                presenter.onClearHistoryClick();
                            }

                            @Override
                            public void onCancel(OperationDialog dialog, View v) {
                            }
                        }).build().show();
                
                break;
            case R.id.cv_friend_manage_enter_btn:
                new OperationDialog.Builder(this).setContent("确定删除好友吗？")
                        .setOnClickItemListener(new OperationDialog.OnClickItemListener() {
                            @Override
                            public void onEnter(OperationDialog dialog, View v) {
                                presenter.onDeleteFriendClick();
                            }

                            @Override
                            public void onCancel(OperationDialog dialog, View v) {
                            }
                        }).build().show();
                
                break;
            default:
                break;
        }
    }

    @Override
    protected void initWidget() {
        notificationItem.getSwitchBtn().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onForbiddenSwitch(isChecked);
            }
        });
        topItem.getSwitchBtn().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onConversationSwitch(isChecked);
            }
        });
    }

    @Override
    public void updateNotification(boolean isOpen) {
        notificationItem.getSwitchBtn().setChecked(isOpen);
    }

    @Override
    public void updateIsTop(boolean isTop) {
        topItem.getSwitchBtn().setChecked(isTop);
    }
}

