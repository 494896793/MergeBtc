package com.bochat.app.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.OperationDialog;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.conversation.GroupDetailContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.common.router.RouterHeaderDetail;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 18:01
 * Description :
 */

@Route(path = RouterGroupDetail.PATH)
public class GroupDetailActivity extends BaseActivity<GroupDetailContract.Presenter> implements GroupDetailContract.View {

    @Inject
    GroupDetailContract.Presenter presenter;

    @BindView(R.id.cv_group_top_bar)
    BoChatTopBar topBar;
    @BindView(R.id.cv_group_detail_banner)
    RelativeLayout banner;
    @BindView(R.id.cv_group_detail_name)
    TextView name;
    @BindView(R.id.cv_group_detail_date)
    TextView date;
    @BindView(R.id.group_head_img)
    SpImageView group_head;
    @BindView(R.id.cv_group_detail_description)
    TextView description;

    @BindView(R.id.cv_group_detail_id)
    BoChatItemView id;
    @BindView(R.id.cv_group_detail_member)
    BoChatItemView member;
    @BindView(R.id.cv_group_detail_add_member)
    BoChatItemView addMember;
    @BindView(R.id.cv_group_detail_manager)
    BoChatItemView manager;
    @BindView(R.id.cv_group_detail_history)
    BoChatItemView history;
    @BindView(R.id.cv_group_detail_clear_history)
    BoChatItemView clearHistory;
    @BindView(R.id.cv_group_detail_notification)
    BoChatItemView notification;
    @BindView(R.id.cv_group_detail_conversation_top_switch)
    BoChatItemView conversationTopSwitch;

    @BindView(R.id.cv_group_detail_enter_btn)
    Button enterBtn;

    private GroupEntity mGroupEntity;

    @OnClick({R.id.cv_group_detail_member, R.id.cv_group_detail_add_member, R.id.cv_group_detail_manager,
            R.id.cv_group_detail_history, R.id.cv_group_detail_notification, R.id.cv_group_detail_enter_btn,
            R.id.cv_group_detail_qr_code, R.id.cv_group_detail_clear_history, R.id.group_head_img})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.cv_group_detail_member:
                presenter.onGroupMemberClick();
                break;
            case R.id.cv_group_detail_add_member:
                presenter.onAddMemberClick();
                break;
            case R.id.cv_group_detail_manager:
                presenter.onGroupManageClick();
                break;
            case R.id.cv_group_detail_history:
                presenter.onSearchHistoryClick();
                break;
            case R.id.cv_group_detail_clear_history:
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
            case R.id.cv_group_detail_enter_btn:
                presenter.onEnterBtnClick();
                break;
            case R.id.cv_group_detail_qr_code:
                presenter.onQRCodeClick();
                break;
            case R.id.group_head_img:
                if (mGroupEntity != null) {
                    Router.navigation(new RouterHeaderDetail(mGroupEntity.getGroup_head()));
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupDetailContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_detail);
    }

    @Override
    public void updateGroupChatDetail(GroupEntity groupEntity, boolean isJoin, boolean isManager, boolean isNotification, boolean isTop) {
        mGroupEntity = groupEntity;
        history.setVisibility(isJoin ? View.VISIBLE : View.GONE);
        notification.setVisibility(isJoin ? View.VISIBLE : View.GONE);
        conversationTopSwitch.setVisibility(isJoin ? View.VISIBLE : View.GONE);
        clearHistory.setVisibility(isJoin ? View.VISIBLE : View.GONE);
        if (isJoin) {
            conversationTopSwitch.getSwitchBtn().setChecked(isTop);
            topBar.setTitleText("群聊设置");
            if (groupEntity.getJoinMethod() != 3) {
                addMember.setVisibility(View.VISIBLE);
            }
            if (isManager) {
                manager.setVisibility(View.VISIBLE);
                enterBtn.setText("解散群聊");
            } else {
                enterBtn.setText("退出群聊");
            }
        } else {
            topBar.setTitleText(groupEntity.getGroup_name()+"("+groupEntity.getPeople()+"人)");
            enterBtn.setText("加入群聊");
        }
        if (groupEntity != null) {
            Glide.with(this).load(groupEntity.getGroup_head()).into(group_head);
            id.getContentView().setText(String.valueOf(groupEntity.getGroup_id()));
            name.setText(groupEntity.getGroup_name());
            date.setText(getResources().getString(R.string.cv_group_detail_create_date, groupEntity.getCreate_time()));
            description.setText(TextUtils.isEmpty(groupEntity.getGroup_introduce()) ? "无" : groupEntity.getGroup_introduce());

            notification.getSwitchBtn().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    presenter.onChangeNotificationClick(isChecked);
                }
            });
            conversationTopSwitch.getSwitchBtn().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    presenter.onConversationTopClick(isChecked);
                }
            });
            member.getContentView().setText(groupEntity.getPeople() + "人");
        }
        notification.getSwitchBtn().setChecked(isNotification);
    }
}
