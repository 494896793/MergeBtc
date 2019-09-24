package com.bochat.app.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.conversation.EditAddFriendApplyContract;
import com.bochat.app.common.router.RouterAddFriendEditApply;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/19 14:36
 * Description :
 */

@Route(path = RouterAddFriendEditApply.PATH)
public class AddFriendEditRequestActivity extends BaseActivity<EditAddFriendApplyContract.Presenter> implements EditAddFriendApplyContract.View {

    @Inject
    EditAddFriendApplyContract.Presenter presenter;
    
    @BindView(R.id.friend_icon)
    SpImageView icon;
    @BindView(R.id.friend_name)
    TextView name;
    @BindView(R.id.friend_signature)
    TextView signature;
    @BindView(R.id.edit_request_input)
    EditText input;
    
    @Override
    public void updateFriendDetail(UserEntity userEntity, FriendEntity info) {
        Glide.with(this).load(info.getHead_img()).into(icon);
        name.setText(info.getNickname());
        String sex = info.getSex() == 1 ? "男  " : "女  ";
        String age = info.getAge() + "岁  ";
        String area = TextUtils.isEmpty(info.getArea()) ? "深圳" : info.getArea();
        signature.setText(sex + age + area);
        String tip = "你好，我是" + userEntity.getNickname() + "。";
        input.setText(tip);
        input.setSelection(tip.length());
    }
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected EditAddFriendApplyContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_friend_edit_request);
    }

    @Override
    protected void initWidget() {
    
    }

    @OnClick({R.id.edit_request_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.onSendButtonClick(input.getText().toString());
    }
}