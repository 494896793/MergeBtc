package com.bochat.app.app.activity.bill;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.CodeCreator;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.util.ScreenShot;
import com.bochat.app.app.util.TextViewUtil;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.ShareDialog;
import com.bochat.app.common.contract.bill.TokenTransferReceiveContract;
import com.bochat.app.common.router.RouterTokenTransferReceive;
import com.bochat.app.common.util.CopyUtil;
import com.bochat.app.model.bean.CurrencyDetailEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  17:40
 * Description : 接收币种
 */
@Route(path = RouterTokenTransferReceive.PATH)
public class TokenTransferReceiveActivity extends BaseActivity<TokenTransferReceiveContract.Presenter> implements TokenTransferReceiveContract.View {
    @Inject
    TokenTransferReceiveContract.Presenter presenter;

    @BindView(R.id.mine_token_transfer_receive_top_bar)
    BoChatTopBar boChatTopBar;
    @BindView(R.id.mine_token_transfer_receive_token_code)
    ImageView imageView;
    @BindView(R.id.mine_token_transfer_receive_token_icon)
    ImageView icon;
    @BindView(R.id.mine_token_transfer_receive_token_name)
    TextView name;
    @BindView(R.id.mine_token_transfer_receive_token_address)
    TextView address;
    @BindView(R.id.mine_token_transfer_type_tips)
    TextView tips;
    @BindView(R.id.mine_token_transfer_receive_layout)
    RelativeLayout mScreenLayout;

    private ScreenShot mScreenShot;
    private String addressText = "";

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected TokenTransferReceiveContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onRightExtButtonClick() {
                super.onRightExtButtonClick();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mScreenShot = new ScreenShot.Builder(getViewContext())
                            .setOnCaptureScreenListener(new ScreenShot.OnCaptureScreenListener() {
                                @Override
                                public void onSuccess(String path, Bitmap bitmap) {
                                    mScreenShot.release();
                                    ShareDialog shareDialog = new ShareDialog(getViewContext(), bitmap);
                                    shareDialog.showPopupWindow();
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    showTips(new ResultTipsType("创建分享图片失败", false));
                                }
                            }).build();
                    mScreenShot.screenshot((Activity) getViewContext());
                } else {
                    Bitmap bitmap = mScreenShot.screenshot(mScreenLayout);
                    if (bitmap != null) {
                        ShareDialog shareDialog = new ShareDialog(getViewContext(), bitmap);
                        shareDialog.showPopupWindow();
                    } else {
                        showTips(new ResultTipsType("创建分享图片失败", false));
                    }
                }
            }
        });
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_token_transfer_receive);
    }

    @OnClick({R.id.mine_token_transfer_receive_select_coin, R.id.mine_token_transfer_receive_token_address})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        if (view.getId() == R.id.mine_token_transfer_receive_select_coin) {
            presenter.onChooseClick();
        } else {
            if (CopyUtil.copy(this, addressText)) {
                showTips("复制成功");
            } else {
                showTips(new ResultTipsType("复制失败", false));
            }
        }
    }

    @Override
    public void updateQRCode(String content) {
        imageView.setImageBitmap(CodeCreator.createQRCode(content, ResourceUtils.dip2px(this, R.dimen.dp_160),
                ResourceUtils.dip2px(this, R.dimen.dp_160), null));
    }

    @Override
    public void updateTokenInfo(CurrencyDetailEntity info) {
        Glide.with(this).load(info.getBIamge()).into(icon);
        name.setText(info.getBName());
        addressText = info.getAddress();
        if (!TextUtils.isEmpty(addressText)) {
            TextViewUtil.appendImage(address, addressText, R.mipmap.ic_wallet_receive_copy);
        }
        tips.setText(String.format(getResources().getString(R.string.mine_token_transfer_type_tips),
                info.getBName()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mScreenShot != null)
            mScreenShot.captureResult(requestCode, resultCode, data);
    }
}
