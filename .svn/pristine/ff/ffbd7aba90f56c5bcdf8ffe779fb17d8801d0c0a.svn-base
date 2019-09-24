package com.bochat.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bochat.app.app.view.DownloadDialog;
import com.bochat.app.app.view.OperationDialog;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterCrashHandle;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.UpgradeEntity;
import com.bochat.app.model.modelImpl.UpgradeModel;
import com.bochat.app.model.util.APKUtils;
import com.bochat.app.model.util.download.DownloadApk;
import com.bochat.app.mvp.view.BaseActivity;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/10 11:08
 * Description :
 */

@Route(path = RouterCrashHandle.PATH)
public class CrashHandleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_crash_handle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        APKUtils.checkUpdate(this, new UpgradeModel(), new APKUtils.OnCheckUpdateListener() {
            @Override
            public void onUpdate(final UpgradeEntity entity) {
                DownloadApk.saveUpgradeEntity(CrashHandleActivity.this, entity);
                DownloadDialog downloadDialog = new DownloadDialog.Builder(CrashHandleActivity.this)
                        .setTitle(CrashHandleActivity.this.getResources().getString(R.string.update_descriptions))
                        .setVersion("V" + entity.getVersion())
                        .setContent(entity.getContent())
                        .forceUpdate(true)
                        .setOnClickItemListener(new DownloadDialog.OnClickItemListener() {
                            @Override
                            public void onEnter(DownloadDialog dialog, View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(entity.getAddress()));
                                if (intent.resolveActivity(CrashHandleActivity.this.getPackageManager()) != null) {
                                    CrashHandleActivity.this.startActivity(intent);
                                } else {
                                    Router.navigation(new RouterDynamicWebView(entity.getAddress(),null, null,null));
                                }
                            }

                            @Override
                            public void onCancel(DownloadDialog dialog, View v) {
                                dialog.dismiss();
                            }
                        }).build();
                downloadDialog.show();
            }

            @Override
            public void onThrow(Throwable throwable) {
                ULog.d("Update-Throwable:%@", throwable);
                OperationDialog oDialog = new OperationDialog.Builder(CrashHandleActivity.this)
                        .strongTip(true)
                        .setContent("网络出现异常，即将退出程序")
                        .setOnClickItemListener(new OperationDialog.OnClickItemListener() {
                            @Override
                            public void onEnter(OperationDialog dialog, View v) {
                                BaseActivity.getTop().finishAll(true);
                                System.exit(0);
                            }

                            @Override
                            public void onCancel(OperationDialog dialog, View v) {
                            }
                        })
                        .build();
                oDialog.show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            OperationDialog oDialog = new OperationDialog.Builder(CrashHandleActivity.this)
                    .strongTip(true)
                    .setContent("网络出现异常，即将退出程序")
                    .setOnClickItemListener(new OperationDialog.OnClickItemListener() {
                        @Override
                        public void onEnter(OperationDialog dialog, View v) {
                            BaseActivity.getTop().finishAll(true);
                            System.exit(0);
                        }

                        @Override
                        public void onCancel(OperationDialog dialog, View v) {
                        }
                    })
                    .build();
            oDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
}
