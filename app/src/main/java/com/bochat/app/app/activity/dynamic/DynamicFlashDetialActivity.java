package com.bochat.app.app.activity.dynamic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.CodeCreator;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.util.ScreenShot;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.ShareDialog;
import com.bochat.app.common.contract.dynamic.DynamicFlashDetialContract;
import com.bochat.app.common.router.RouterDynamicFlashDetail;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.DynamicFlashEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * create by guoying ${Date} and ${Month}
 */
@Route(path = RouterDynamicFlashDetail.PATH)
public class DynamicFlashDetialActivity extends BaseActivity<DynamicFlashDetialContract.Presenter> implements DynamicFlashDetialContract.View {
    @BindView(R.id.dynamic_flash_bochat_bar)
    BoChatTopBar boChatTopBar;
    @BindView(R.id.detail_rise)
    TextView riseText;
    @BindView(R.id.detail_decline)
    TextView declineText;
    @BindView(R.id.flash_detail_title)
    TextView title;
    @BindView(R.id.flash_detail_content)
    TextView content;
    @BindView(R.id.flash_date)
    TextView date;
    @BindView(R.id.qr_code_img)
    ImageView qrCode;
    @BindView(R.id.flash_layout)
    RelativeLayout relativeLayout;

    @BindView(R.id.screen_shot_layout)
    RelativeLayout mScreenLayout;

    @Inject
    DynamicFlashDetialContract.Presenter presenter;

    private ScreenShot mScreenShot;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected DynamicFlashDetialContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dynamic_flash_detail);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onRightExtButtonClick() {
                presenter.onShareClick();
            }

            @Override
            public void onActivityFinish() {
                super.onActivityFinish();
                presenter.setBackData();
            }
        });
    }

    @Override
    public void onUpdateView(DynamicFlashEntity entity) {
        riseText.setText("利好" + entity.getAdmire());
        declineText.setText("利空" + entity.getTrample());
        title.setText(entity.getTitle());
        content.setText(entity.getContent());
        String dateStr = getStrTime(entity.getCreateTime());
        date.setText(dateStr);

    }

    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    private Bitmap createQRCode(String url) {
        return CodeCreator.createQRCode(url, ResourceUtils.dip2px(this, R.dimen.dp_70),
                ResourceUtils.dip2px(this, R.dimen.dp_70), null);
    }

    @Override
    public void showShareDialog(String shareUrl) {


        mScreenShot = new ScreenShot.Builder(this)
                .setOnCaptureScreenListener(new ScreenShot.OnCaptureScreenListener() {
                    @Override
                    public void onSuccess(String path, Bitmap bitmap) {
                        ShareDialog shareDialog = new ShareDialog(getViewContext(), bitmap);
                        shareDialog.showPopupWindow();
                        mScreenShot.release();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        // showTips("保存海报失败");
                        ULog.d("ScreenShot-onError:%@", throwable);
                    }
                }).build();
        mScreenShot.screenshot(relativeLayout);

      /*  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mScreenShot = new ScreenShot.Builder(this)
                    .setOnCaptureScreenListener(new ScreenShot.OnCaptureScreenListener() {
                        @Override
                        public void onSuccess(String path, Bitmap bitmap) {
                            ShareDialog shareDialog = new ShareDialog(getViewContext(), bitmap);
                            shareDialog.showPopupWindow();
                            mScreenShot.release();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            // showTips("保存海报失败");
                            ULog.d("ScreenShot-onError:%@",throwable);
                        }
                    }).build();
            mScreenShot.screenshot(this);
        } else {
            mScreenShot = new ScreenShot.Builder(this).build();
            mScreenShot.screenshot(mScreenLayout);
        }*/
    }

    @Override
    public void onUpdateQrCode(String loadUrl) {
        Glide.with(this).load(createQRCode(loadUrl)).into(qrCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mScreenShot != null)
            mScreenShot.captureResult(requestCode, resultCode, data);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            presenter.setBackData();
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}