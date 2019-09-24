package com.bochat.app.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.conversation.FriendDetailContact;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterHeaderDetail;
import com.bochat.app.common.util.CopyUtil;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.util.TextUtil;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bochat.app.app.view.SpImageView.TYPE_CIRCLE;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/19 09:05
 * Description :
 */

@Route(path = RouterFriendDetail.PATH)
public class FriendDetailActivity extends BaseActivity<FriendDetailContact.Presenter> implements FriendDetailContact.View {

    @Inject
    FriendDetailContact.Presenter presenter;

    @BindView(R.id.friend_detail_top_bar)
    BoChatTopBar topBar;

    @BindView(R.id.friend_detail_icon)
    SpImageView icon;
    @BindView(R.id.friend_detail_name)
    TextView name;
    @BindView(R.id.friend_detail_description)
    TextView detail;

    @BindView(R.id.friend_detail_id)
    BoChatItemView id;
    @BindView(R.id.friend_detail_code)
    BoChatItemView code;
    @BindView(R.id.friend_detail_phone)
    BoChatItemView phone;

    @BindView(R.id.friend_detail_sex)
    BoChatItemView sex;
    @BindView(R.id.friend_detail_age)
    BoChatItemView age;
    @BindView(R.id.friend_detail_area)
    BoChatItemView area;

    @BindView(R.id.friend_detail_enter_btn)
    Button enterBtn;

    private boolean isFriend;

    private FriendEntity mFriendEntity;

    @Override
    public void updateFriendDetail(FriendEntity info) {
        mFriendEntity = info;
        icon.setType(TYPE_CIRCLE);
        Glide.with(this).load(info.getHead_img()).into(icon);

        name.setText(info.getNickname());
        id.getContentView().setText(String.valueOf(info.getId()));
        phone.getContentView().setText(info.getAccount());
        sex.getContentView().setText(info.getSexString());
        age.getContentView().setText(String.valueOf(info.getAge()));
        String areaString = info.getCountries();
        if (!TextUtils.isEmpty(info.getProvince())) {
            areaString = areaString + "-" + info.getProvince();
        }
        if (!TextUtils.isEmpty(info.getCity())) {
            areaString = areaString + "-" + info.getCity();
        }
        if (TextUtils.isEmpty(areaString)) {
            areaString = info.getAddress();
        }
        if (!TextUtil.isEmptyString(areaString)) {
            area.getContentView().setText(areaString);
        } else {
            area.setVisibility(View.INVISIBLE);
        }

        isFriend = presenter.isFriend();
        enterBtn.setText(isFriend ? "发送消息" : "申请好友");
        enterBtn.setVisibility(presenter.isMe() ? View.INVISIBLE : View.VISIBLE);
        phone.setVisibility(isFriend ? View.VISIBLE : View.GONE);
        topBar.getTextButton().setVisibility(presenter.isManagable() ? View.VISIBLE : View.INVISIBLE);
        detail.setText(TextUtils.isEmpty(info.getSignature()) ? "这个用户很懒，什么东西也没留下。" : info.getSignature());
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected FriendDetailContact.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_friend_detail);
    }

    @Override
    protected void initWidget() {
        topBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onTextExtButtonClick() {
                presenter.onManageBtnClick();
            }
        });
    }

    @OnClick({R.id.friend_detail_enter_btn, R.id.friend_detail_code, R.id.friend_detail_id, R.id.friend_detail_phone, R.id.friend_detail_icon})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {

            case R.id.friend_detail_id:
                boolean copy = CopyUtil.copy(this, id.getContentView().getText().toString());
                showTips(copy ? new ResultTipsType("博信ID复制成功") : new ResultTipsType("博信ID复制失败", false));
                break;

            case R.id.friend_detail_phone:
                boolean copyPhone = CopyUtil.copy(this, phone.getContentView().getText().toString());
                showTips(copyPhone ? new ResultTipsType("手机号码复制成功") : new ResultTipsType("手机号码复制失败", false));
                break;

            case R.id.friend_detail_code:
                presenter.onQRCodeClick();
                break;

            case R.id.friend_detail_enter_btn:
                if (isFriend) {
                    presenter.onStartConversationBtnClick();
                } else {
                    presenter.onAddFriendBtnClick();
                }
                break;
            case R.id.friend_detail_icon:
                if (mFriendEntity != null){
                    Router.navigation(new RouterHeaderDetail(mFriendEntity.getHead_img()));
                }
                break;
            default:
                break;
        }
    }
}
