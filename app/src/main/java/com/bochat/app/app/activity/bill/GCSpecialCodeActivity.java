package com.bochat.app.app.activity.bill;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.CodeCreator;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.util.ScreenShot;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.ShareDialog;
import com.bochat.app.common.contract.bill.GCSpecialCodeContract;
import com.bochat.app.common.router.RouterGCSpecialCode;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  17:40
 * Description : 专用码
 */
@Route(path = RouterGCSpecialCode.PATH)
public class GCSpecialCodeActivity extends BaseActivity<GCSpecialCodeContract.Presenter> implements GCSpecialCodeContract.View {

    @Inject
    GCSpecialCodeContract.Presenter presenter;

    @BindView(R.id.mine_token_transfer_receive_gc_code)
    ImageView imageView;

    @BindView(R.id.mine_token_transfer_gc_layout)
    RelativeLayout mScreenLayout;

    @BindView(R.id.mine_token_transfer_receive_gc_top_bar)
    BoChatTopBar boChatTopBar;

    private ScreenShot mScreenShot;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GCSpecialCodeContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_gc_special_code);
    }

    @Override
    public void updateQRCode(String content) {
        imageView.setImageBitmap(CodeCreator.createQRCode(content, ResourceUtils.dip2px(this, R.dimen.dp_160),
                ResourceUtils.dip2px(this, R.dimen.dp_160), null));
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

    @OnClick(R.id.mine_token_transfer_receive_gc_tips2)
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mScreenShot = new ScreenShot.Builder(this)
                    .setOnCaptureScreenListener(new ScreenShot.OnCaptureScreenListener() {
                        @Override
                        public void onSuccess(String path, Bitmap bitmap) {
                            showTips("保存成功");
                            mScreenShot.release();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            showTips(new ResultTipsType("保存失败", false));
                        }
                    }).build();
            mScreenShot.screenshot(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mScreenShot != null)
            mScreenShot.captureResult(requestCode, resultCode, data);
    }
}
