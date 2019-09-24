package com.bochat.app.app.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.CodeCreator;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.util.ScreenShot;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.mine.InvitationContract;
import com.bochat.app.common.router.RouterInvitation;
import com.bochat.app.common.util.ShareUtil;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.InviteCodeEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:56
 * Description :
 */

@Route(path = RouterInvitation.PATH)
public class InvitationActivity extends BaseActivity<InvitationContract.Presenter> implements InvitationContract.View {

    private static final String SHARE_TITLE = "邀请有礼";
    private static final String SHARE_DESCRIPTION = "邀请链接 [点击复制] ";

    @Inject
    InvitationContract.Presenter presenter;

    @BindView(R.id.invitation_top_bar)
    BoChatTopBar mInvitationTopBar;
    @BindView(R.id.invitation_user_header)
    SpImageView mInvitationUserHeader;
    @BindView(R.id.invitation_user_name)
    TextView mInvitationUserName;
    @BindView(R.id.invitation_user_id)
    TextView mInvitationUserId;
    @BindView(R.id.invitation_qr_code)
    ImageView mInvitationQrCode;
    @BindView(R.id.invitation_success_member)
    TextView mInvitationSuccessMember;
    @BindView(R.id.invitation_code_text)
    TextView mInvitationCode;
    @BindView(R.id.screen_layout)
    LinearLayout mScreenLayout;

    private String mShareUrl = "";
    private String mSharedDesc = "";

    private ScreenShot mScreenShot;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected InvitationContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_invitation);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mInvitationUserHeader.setType(SpImageView.TYPE_CIRCLE);
    }

    @Override
    public void updateInfo(InviteCodeEntity inviteCodeEntity) {

        mShareUrl = inviteCodeEntity.getShareUrl();
        mSharedDesc = SHARE_DESCRIPTION + mShareUrl;

        Glide.with(this).load(inviteCodeEntity.getHeadImg()).into(mInvitationUserHeader);
        Glide.with(this).load(createQRCode(inviteCodeEntity.getShareUrl())).into(mInvitationQrCode);
        mInvitationUserName.setText(inviteCodeEntity.getNickname());
        mInvitationUserId.setText(getString(R.string.invitation_user_id_format, inviteCodeEntity.getUserId()));
        mInvitationSuccessMember.setText(getString(R.string.mine_invite, inviteCodeEntity.getCount()));
        mInvitationCode.setText(getString(R.string.invitation_code, inviteCodeEntity.getInviteCode()));

    }

    private Bitmap createQRCode(String url) {
        return CodeCreator.createQRCode(url, ResourceUtils.dip2px(this, R.dimen.dp_132),
                ResourceUtils.dip2px(this, R.dimen.dp_132), null);
    }

    @OnClick({R.id.invitation_share_wx_friends, R.id.invitation_share_wx_circle_friends, R.id.invitation_share_qq, R.id.invitation_share_save_poster})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);

        switch (view.getId()) {
            case R.id.invitation_share_wx_friends:
                ShareUtil.getInstance().shareUrlToWx(mShareUrl, SHARE_TITLE, mSharedDesc, SendMessageToWX.Req.WXSceneSession);
                break;
            case R.id.invitation_share_wx_circle_friends:
                ShareUtil.getInstance().shareUrlToWx(mShareUrl, SHARE_TITLE, mSharedDesc, SendMessageToWX.Req.WXSceneTimeline);
                break;
            case R.id.invitation_share_qq:
                ShareUtil.getInstance().shareToQQ(this, mShareUrl, SHARE_TITLE, mSharedDesc);
                break;
            case R.id.invitation_share_save_poster:

//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    mScreenShot = new ScreenShot.Builder(this)
//                            .setOnCaptureScreenListener(new ScreenShot.OnCaptureScreenListener() {
//                                @Override
//                                public void onSuccess(String path, Bitmap bitmap) {
//                                    showTips("保存海报成功");
//                                    mScreenShot.release();
//                                }
//
//                                @Override
//                                public void onError(Throwable throwable) {
////                                showTips("保存海报失败");
//                                    ULog.d("ScreenShot-onError:%@",throwable);
//                                }
//                            }).build();
//                    mScreenShot.screenshot(this);
//                } else {
//                    mScreenShot = new ScreenShot.Builder(this).build();
//                    mScreenShot.screenshot(mScreenLayout);
//                }
                mScreenShot = new ScreenShot.Builder(this)
                        .setOnCaptureScreenListener(new ScreenShot.OnCaptureScreenListener() {
                            @Override
                            public void onSuccess(String path, Bitmap bitmap) {
                                showTips("保存海报成功");
                                mScreenShot.release();
                            }

                            @Override
                            public void onError(Throwable throwable) {
//                                showTips("保存海报失败");
                                ULog.d("ScreenShot-onError:%@", throwable);
                            }
                        }).build();
                mScreenShot.screenshot(mScreenLayout);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mScreenShot != null)
            mScreenShot.captureResult(requestCode, resultCode, data);
    }
}
