package com.bochat.app.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bochat.app.R;
import com.bochat.app.app.ActivityComponent;
import com.bochat.app.app.DaggerActivityComponent;
import com.bochat.app.app.activity.SplashScreenActivity;
import com.bochat.app.app.activity.WebActivity;
import com.bochat.app.app.view.DownloadDialog;
import com.bochat.app.app.view.LoadingDialog;
import com.bochat.app.app.view.ToastHelper;
import com.bochat.app.common.router.AbstractRouter;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.UpgradeEntity;
import com.bochat.app.model.modelImpl.UpgradeModel;
import com.bochat.app.model.util.APKUtils;
import com.bochat.app.model.util.download.DownloadApk;
import com.bochat.app.mvp.injector.ApplicationComponentProvider;
import com.bochat.app.mvp.injector.module.ActivityModule;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import crossoverone.statuslib.StatusUtil;
import io.reactivex.disposables.Disposable;
import razerdp.basepopup.BasePopupWindow;


/**
 * Author:         FJ
 * Version:        1.0
 * CreateDate:     2019/04/08 09:49
 * Description:
 */

public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView<P> {

    private ActivityComponent activityComponent;

    private LoadingDialog loadingDialog;

    private boolean isLoadingDismiss;

    protected P presenter;

    protected abstract void initInjector();

    protected abstract P initPresenter();

    protected abstract void setRootView(Bundle savedInstanceState);

    private static ArrayList<BaseActivity> activities = new ArrayList<>();

    private static BaseActivity topActivity;

    public static boolean isUpdated = false;

    public static boolean isChecked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(ApplicationComponentProvider.getApplicationComponent())
                .build();


        initInjector();
        presenter = initPresenter();
        ARouter.getInstance().inject(this);
        setStatusBarColor(getDefaultStatusBarColor());
        setStatusBarTextColor(getDefaultStatusBarTextColor());
        setRootView(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
        activities.add(this);
    }

    public static String getRandomString(int length) {
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ULog.d("Activity:%@", this);
        if (presenter != null) {
            presenter.onViewAttached(this);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static final int IS_FORCE_UPDATE = 1;

    @Override
    protected void onResume() {
        super.onResume();
        topActivity = this;
        if (isRefreshView()) {
            presenter.onViewRefresh();
            if (!isChecked && !isUpdated && !(getTop() instanceof SplashScreenActivity) && !(getTop() instanceof WebActivity)) {
                isChecked = true;
                APKUtils.checkUpdate(getTop(), new UpgradeModel(), new APKUtils.OnCheckUpdateListener() {
                    @Override
                    public void onUpdate(final UpgradeEntity entity) {
                        DownloadApk.saveUpgradeEntity(getTop(), entity);
                        DownloadDialog downloadDialog = new DownloadDialog.Builder(getTop())
                                .setTitle(getResources().getString(R.string.update_descriptions))
                                .setVersion("V" + entity.getVersion())
                                .setContent(entity.getContent())
                                .forceUpdate(entity.is_update == IS_FORCE_UPDATE)
                                .setOnClickItemListener(new DownloadDialog.OnClickItemListener() {
                                    @Override
                                    public void onEnter(DownloadDialog dialog, View v) {
                                        if (dialog.isForceUpdate()) {
                                            BaseActivity.isChecked = false;
                                        }
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(entity.getAddress()));
                                        if (intent.resolveActivity(getPackageManager()) != null) {
                                            startActivity(intent);
                                        } else {
                                            Router.navigation(new RouterDynamicWebView(entity.getAddress(),null, null,null));
                                        }
                                    }

                                    @Override
                                    public void onCancel(DownloadDialog dialog, View v) {
                                        if (dialog.isForceUpdate()) {
                                            BaseActivity.isChecked = false;
                                            finishAll(true);
                                        }
                                        dialog.dismiss();
                                    }
                                }).build();
                        downloadDialog.show();
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onViewInactivation();
        }
    }

    protected boolean isRefreshView() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onViewDetached();
        }
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        activities.remove(this);
    }

    @Override
    public void showTips(final TipsType tip) {
        int icon = R.mipmap.success_icon;
        if (tip instanceof ResultTipsType && !((ResultTipsType) tip).isSuccess()) {
            icon = R.mipmap.tips_icon;
        }
        Toast toast = ToastHelper.makeToast(BaseActivity.this, tip.getMessage(), icon);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showTips(final String tip) {
        showTips(new ResultTipsType(tip, true));
    }

    //TODO back键 finish activity
    @Override
    public void showLoading(final Object extras) {
        isLoadingDismiss = false;
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(BaseActivity.this);
                }
                loadingDialog.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (extras instanceof Disposable) {
                            if (!((Disposable) extras).isDisposed()) {
                                ((Disposable) extras).dispose();
                            }
                        }
                    }
                });
                if (presenter.isActive() && !isLoadingDismiss) {
                    loadingDialog.showPopupWindow();
                }
            }
        }, 0);
    }

    @Override
    public void hideLoading(Object extras) {
        isLoadingDismiss = true;
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (extras instanceof String) {
            if (!TextUtils.isEmpty((String) extras)) {
                showTips(new ResultTipsType((String) extras));
            }
        }
    }

    @Override
    public void finishAll(boolean containSelf) {
        if (!activities.isEmpty()) {
            for (BaseActivity activity : activities) {
                if (containSelf || !activity.equals(BaseActivity.this)) {
                    activity.finish();
                }
            }
        }
    }

    public static BaseActivity getTop() {
        return topActivity;
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public Intent getViewIntent() {
        return getIntent();
    }

    @Override
    public void setViewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    public <T> T getExtra(Class<T> type){
        try {
            return (T)(getViewIntent().getSerializableExtra(AbstractRouter.TAG));
        } catch (Exception e){
        }
        return null;
    }
    
    protected ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    protected void initWidget() {

    }

    protected void onViewClicked(View view) {

    }

    public IBasePresenter getPresenter() {
        return presenter;
    }

    /**
     * Author      : FJ
     * CreateDate  : 2019/5/5 0005 下午 7:19
     * Description : 沉浸式    StatusBarUtil.setTranslucent(this);
     * 背景色    StatusBarUtil.setColorNoTranslucent(this, Color.parseColor("#FFFFFF"));
     * 字体颜色  StatusUtil.setSystemStatus(this, false, true);
     */

    protected String getDefaultStatusBarTextColor() {
        return "#000000";
    }

    protected String getDefaultStatusBarColor() {
        return null;
    }

    protected void setStatusBarTextColor(String color) {
        StatusUtil.setSystemStatus(this, true, "#000000".equals(color));
    }

    protected void setStatusBarColor(String color) {
        if (color != null) {
            StatusBarUtil.setColorNoTranslucent(this, Color.parseColor(color));
        } else {
            NavigationBarUtil.assistActivity(findViewById(android.R.id.content));
            StatusBarUtil.setTranslucent(this);
        }
    }


    public static class NavigationBarUtil {

        public static void assistActivity(View content) {
            new NavigationBarUtil(content);
        }

        private View mChildOfContent;
        private int usableHeightPrevious;
        private ViewGroup.LayoutParams frameLayoutParams;

        private NavigationBarUtil(View content) {
            mChildOfContent = content;
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    possiblyResizeChildOfContent();
                }
            });
            frameLayoutParams = mChildOfContent.getLayoutParams();
        }

        private void possiblyResizeChildOfContent() {
            int usableHeightNow = computeUsableHeight();
            if (usableHeightNow != usableHeightPrevious) {

                //将计算的可视高度设置成视图的高度
                frameLayoutParams.height = usableHeightNow;
                mChildOfContent.requestLayout();//请求重新布局
                usableHeightPrevious = usableHeightNow;
            }
        }

        private int computeUsableHeight() {
            //计算视图可视高度
            Rect r = new Rect();
            mChildOfContent.getWindowVisibleDisplayFrame(r);
            //        这里是调整顶起的高度，可以直接用r.bottom
            return (r.bottom);
        }
    }
}