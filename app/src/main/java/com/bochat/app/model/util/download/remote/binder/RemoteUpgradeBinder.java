package com.bochat.app.model.util.download.remote.binder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.bochat.app.IRemoteUpgradeManager;
import com.bochat.app.R;
import com.bochat.app.app.view.DownloadDialog;
import com.bochat.app.app.view.OperationDialog;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.UpgradeEntity;
import com.bochat.app.model.modelImpl.UpgradeModel;
import com.bochat.app.model.util.APKUtils;
import com.bochat.app.model.util.download.DownloadApk;
import com.bochat.app.mvp.view.BaseActivity;

public class RemoteUpgradeBinder extends IRemoteUpgradeManager.Stub {

    @Override
    public void checkUpgrade() {
        final Activity topActivity = BaseActivity.getTop();
        if (topActivity != null) {
            APKUtils.checkUpdate(topActivity, new UpgradeModel(), new APKUtils.OnCheckUpdateListener() {
                @Override
                public void onUpdate(final UpgradeEntity entity) {
                    DownloadApk.saveUpgradeEntity(topActivity, entity);
                    DownloadDialog downloadDialog = new DownloadDialog.Builder(topActivity)
                            .setTitle(topActivity.getResources().getString(R.string.update_descriptions))
                            .setVersion("V" + entity.getVersion())
                            .setContent(entity.getContent())
                            .forceUpdate(true)
                            .setOnClickItemListener(new DownloadDialog.OnClickItemListener() {
                                @Override
                                public void onEnter(DownloadDialog dialog, View v) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(entity.getAddress()));
                                    if (intent.resolveActivity(topActivity.getPackageManager()) != null) {
                                        topActivity.startActivity(intent);
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
                    OperationDialog oDialog = new OperationDialog.Builder(BaseActivity.getTop())
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
        } else {
            OperationDialog oDialog = new OperationDialog.Builder(BaseActivity.getTop())
                    .strongTip(true)
                    .setContent("网络出现异常，即将退出程序")
                    .setOnClickItemListener(new OperationDialog.OnClickItemListener() {
                        @Override
                        public void onEnter(OperationDialog dialog, View v) {
                            BaseActivity.getTop().finishAll(true);
                        }

                        @Override
                        public void onCancel(OperationDialog dialog, View v) {
                        }
                    })
                    .build();
            oDialog.show();
        }
    }

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     *
     * @param anInt
     * @param aLong
     * @param aBoolean
     * @param aFloat
     * @param aDouble
     * @param aString
     */
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) {

    }
}
