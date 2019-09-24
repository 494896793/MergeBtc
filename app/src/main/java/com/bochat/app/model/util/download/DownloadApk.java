package com.bochat.app.model.util.download;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.WindowManager;

import com.bochat.app.common.util.SharePreferenceUtil;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.UpgradeEntity;
import com.bochat.app.model.util.TextUtil;

import org.w3c.dom.Text;

import java.io.File;

public final class DownloadApk {

    /**
     * 下载保存
     */
    public static final String DOWNLOAD_SHARED_NAME = "Download_Apk";

    private Builder mBuilder;

    public DownloadApk() {
        throw new IllegalStateException("please use DownloadApk.Builder build 'DownloadApk' Object");
    }

    private DownloadApk(Builder builder) {
        mBuilder = builder;
    }

    /**
     * @return {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * 开始下载
     */
    public void startDownload() {

        final Context context = mBuilder.mContext;
        Intent intent = new Intent(context, DownloadApkService.class);
        intent.putExtra(DownloadApkService.DOWNLOAD_URL, mBuilder.mDownloadUrl);
        intent.putExtra(DownloadApkService.DOWNLOAD_APK_NAME, mBuilder.mName);
        intent.putExtra(DownloadApkService.DOWNLOAD_TITLE, mBuilder.mTitle);
        intent.putExtra(DownloadApkService.DOWNLOAD_DESCRIPTIONS, mBuilder.mDescriptions);
        intent.putExtra(DownloadApkService.DOWNLOAD_IS_FORCE_UPDATE, mBuilder.isForceUpdating);
        context.startService(intent);

        if (mBuilder.isForceUpdating) {
            mBuilder.mDialog = new DownloadApkDialog(mBuilder.mContext);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                mBuilder.mDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
//            } else {
//                mBuilder.mDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
//            }
            mBuilder.mDialog.show();
            mBuilder.mDialog.setDownloadTitle(mBuilder.mTitle);
            mBuilder.mDialog.setDownloadDescriptions(mBuilder.mDescriptions);
            mBuilder.mDialog.setProgress(0);
        }
    }

    public void quit() {
        LocalBroadcastManager.getInstance(mBuilder.mContext).unregisterReceiver(mBuilder.mDownloadReceiver);
    }

    /**
     * 安装
     *
     * @param context {@link Context}
     */
    public static void install(Context context) {
        String apk = context.getSharedPreferences(DOWNLOAD_SHARED_NAME, Context.MODE_PRIVATE).getString(DownloadApkService.DOWNLOAD_APK_NAME, "");
        File file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/" + apk);
        if (file != null && file.exists()) {
            Intent install = new Intent(Intent.ACTION_VIEW);
            Uri downloadFileUri = Uri.fromFile(file);
            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(install);
        }
    }

    /**
     * 版本号
     *
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {

        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionName;

    }

    public static final String UPGRADE_VERSION = "version";
    public static final String UPGRADE_IS_UPDATE = "is_update";
    public static final String UPGRADE_ADDRESS = "address";
    public static final String UPGRADE_CONTENT = "content";
    public static final String UPGRADE_IOS_ADDRESS = "ios_address";
    public static final String UPGRADE_INTERNAL = "internal";

    public static UpgradeEntity getUpgradeEntity(Context context) {
        SharePreferenceUtil.init(context);
        SharedPreferences sharedPreferences = SharePreferenceUtil.getSharePreference();
        String version = sharedPreferences.getString(UPGRADE_VERSION, "");
        int is_update = sharedPreferences.getInt(UPGRADE_IS_UPDATE, 0);
        String address = sharedPreferences.getString(UPGRADE_ADDRESS, "");
        String content = sharedPreferences.getString(UPGRADE_CONTENT, "");
        String ios_address = sharedPreferences.getString(UPGRADE_IOS_ADDRESS, "");
        String internal = sharedPreferences.getString(UPGRADE_INTERNAL, "");
        if(TextUtil.isEmptyString(version))
            return null;
        UpgradeEntity entity = new UpgradeEntity();
        entity.setVersion(version);
        entity.setIs_update(is_update);
        entity.setAddress(address);
        entity.setContent(content);
        entity.setIos_address(ios_address);
        entity.setInternal(internal);
        return entity;
    }

    public static void saveUpgradeEntity(Context context, UpgradeEntity entity) {
        SharePreferenceUtil.init(context);
        SharedPreferences sharedPreferences = SharePreferenceUtil.getSharePreference();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UPGRADE_VERSION, entity.getVersion());
        editor.putInt(UPGRADE_IS_UPDATE, entity.getIs_update());
        editor.putString(UPGRADE_ADDRESS, entity.getAddress());
        editor.putString(UPGRADE_CONTENT, entity.getContent());
        editor.putString(UPGRADE_IOS_ADDRESS, entity.getIos_address());
        editor.putString(UPGRADE_INTERNAL, entity.getInternal());
        editor.apply();
    }

    public static void removeUpgradeEntity(Context context) {
        SharePreferenceUtil.init(context);
        SharedPreferences sharedPreferences = SharePreferenceUtil.getSharePreference();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(UPGRADE_VERSION);
        editor.remove(UPGRADE_IS_UPDATE);
        editor.remove(UPGRADE_ADDRESS);
        editor.remove(UPGRADE_CONTENT);
        editor.remove(UPGRADE_IOS_ADDRESS);
        editor.remove(UPGRADE_INTERNAL);
        editor.apply();
    }

    public static class Builder {

        private Context mContext;
        /**
         * 强制更新
         */
        private boolean isForceUpdating;

        /**
         * 下载apk名称不带后缀
         */
        private String mName;

        /**
         * 下载地址
         */
        private String mDownloadUrl;

        /**
         * 下载标题
         */
        private String mTitle;

        /**
         * 下载描述
         */
        private String mDescriptions;

        private DownloadApkDialog mDialog;
        /**
         *
         */
        private DownloadApkReceiver mDownloadReceiver;

        /**
         * 下载监听器接口
         */
        private OnDownloadApkListener mListener;

        private Looper mDownloadLooper;
        private DownloadApkHandle mDownloadApkHandle;

        private class DownloadApkHandle extends Handler {

            private Builder mBuilder;

            public DownloadApkHandle(Builder builder, Looper looper) {
                super(looper);
                mBuilder = builder;
            }

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case DownloadManager.STATUS_PENDING:
                        ULog.d("STATUS_PENDING");
                        if (mListener != null)
                            mListener.onDownloadPending(mBuilder);
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        int totalSize = msg.arg1;
                        int curSize = msg.arg2;
                        float progress = ((float) curSize / (float) totalSize) * 100;

                        if (isForceUpdating && mDialog != null)
                            mDialog.setProgress((int) progress);

                        if (mListener != null)
                            mListener.onDownloadRunning(mBuilder, progress, curSize, totalSize);
                        break;
                    case DownloadManager.STATUS_FAILED:
                        if (mListener != null)
                            mListener.onDownloadFailed(mBuilder, (int) msg.obj);
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        if (isForceUpdating && mDialog != null)
                            mDialog.dismissAndCancel();

                        if (mListener != null)
                            mListener.onDownloadCompleted();
                        break;
                }

            }
        }

        public Builder() {

//            HandlerThread thread = new HandlerThread("Builder-Handler[" + mName + "]");
//            thread.start();

            mDownloadLooper = Looper.getMainLooper();
            mDownloadApkHandle = new DownloadApkHandle(this, mDownloadLooper);
            mDownloadReceiver = new DownloadApkReceiver(mDownloadApkHandle);

        }

        /**
         * 必须最先调用
         *
         * @param context
         * @return
         */
        public Builder context(Context context) {
            mContext = context;
            return this;
        }

        /**
         * 动态注册广播
         *
         * @return {@link Builder}
         */
        public Builder dynamicReceiver() {
            if (mContext == null)
                throw new IllegalArgumentException("please call Builder context() method set up the Context!");
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(mContext);
            IntentFilter filter = new IntentFilter(DownloadApkService.DOWNLOAD_APK_ACTION);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            lbm.registerReceiver(mDownloadReceiver, filter);
            return this;
        }

        /**
         * 设置下载监听
         *
         * @param listener
         * @return
         */
        public Builder setOnDownloadApkListener(OnDownloadApkListener listener) {
            mListener = listener;
            return this;
        }

        /**
         * 强制更新
         *
         * @param isForceUpdating
         * @return
         */
        public Builder forceUpdate(boolean isForceUpdating) {
            this.isForceUpdating = isForceUpdating;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public String getTitle() {
            return mTitle;
        }

        /**
         * 设置文件名称
         *
         * @param name
         * @return
         */
        public Builder setName(String name) {
            mName = name;
            return this;
        }


        public String getName() {
            return mName;
        }

        /**
         * 设置下载地址
         *
         * @param url
         * @return
         */
        public Builder setDownloadUrl(String url) {
            mDownloadUrl = url;
            return this;
        }

        public String getDownloadUrl() {
            return mDownloadUrl;
        }

        /**
         * 设置下载描述
         *
         * @param descriptions
         * @return
         */
        public Builder setDescriptions(String descriptions) {
            mDescriptions = descriptions;
            return this;
        }

        public String getDescriptions() {
            return mDescriptions;
        }

        public DownloadApkDialog getDialog() {
            return mDialog;
        }

        /**
         * 构建器
         *
         * @return {@link DownloadApk}
         */
        public DownloadApk build() {
            return new DownloadApk(this);
        }

        public static class SimpleDownloadApkListener implements OnDownloadApkListener {

            @Override
            public void onDownloadPending(Builder builder) {

            }

            @Override
            public void onDownloadRunning(Builder builder, float progress, int curSize, int totalSize) {

            }

            @Override
            public void onDownloadFailed(Builder builder, int reason) {

            }

            @Override
            public void onDownloadCompleted() {

            }
        }
        /**
         * 下载监听器
         */
        public interface OnDownloadApkListener {

            void onDownloadPending(Builder builder);

            void onDownloadRunning(Builder builder, float progress, int curSize, int totalSize);

            void onDownloadFailed(Builder builder, int reason);

            void onDownloadCompleted();
        }
    }
}
