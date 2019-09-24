package com.bochat.app.model.util.download;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import java.io.File;

@SuppressLint("Registered")
public class DownloadApkService extends IntentService {

    public static final String DOWNLOAD_URL = "download_url";

    public static final String DOWNLOAD_APK_NAME = "apk_name";

    public static final String DOWNLOAD_TITLE = "title";

    public static final String DOWNLOAD_DESCRIPTIONS = "descriptions";

    public static final String DOWNLOAD_IS_FORCE_UPDATE = "force_update";

    public static final String DOWNLOAD_APK_ACTION = "com.bochat.intent.action.DOWNLOAD_APK";

    public DownloadApkService() {
        this("DownloadApkService");
    }

    public DownloadApkService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        assert intent != null;
        String url = intent.getStringExtra(DOWNLOAD_URL);
        String name = intent.getStringExtra(DOWNLOAD_APK_NAME);
        String title = intent.getStringExtra(DOWNLOAD_TITLE);
        String descriptions = intent.getStringExtra(DOWNLOAD_DESCRIPTIONS);
        boolean isForceUpdate = intent.getBooleanExtra(DOWNLOAD_IS_FORCE_UPDATE, false);
        String apkName = name + ".apk";

        SharedPreferences preferences = getSharedPreferences(DownloadApk.DOWNLOAD_SHARED_NAME, MODE_PRIVATE);
        preferences.edit().putString(DOWNLOAD_APK_NAME, apkName).apply();

        long dmRequestId = downloadApk(url, apkName, title, descriptions, isForceUpdate);
        //发送广播
        sendBroadcast(dmRequestId);

    }

    /**
     * 发送广播通知
     * @param requestId
     */
    void sendBroadcast(long requestId) {
        Intent intent = new Intent(DownloadApkService.DOWNLOAD_APK_ACTION);
        intent.putExtra(DownloadApkReceiver.REQUEST_ID, requestId);
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.sendBroadcast(intent);
    }

    /**
     * 下载apk
     * @param url
     * @param apkName
     * @param title
     * @param descriptions
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    long downloadApk(String url, String apkName, String title, String descriptions, boolean isForceUpdate) {

        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request dmRequest = new DownloadManager.Request(Uri.parse(url));

        File file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/" + apkName);
        if (file != null && file.exists()) {
            file.delete();
        }
        //设置apk保存位置
        dmRequest.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, apkName);
        //设置允许下载的网络类型
        dmRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //设置下载标题
        dmRequest.setTitle(title != null && !TextUtils.isEmpty(title) ? title : "下载");
        //设置下载描述
        dmRequest.setDescription(descriptions != null && !TextUtils.isEmpty(descriptions) ? descriptions : "应用正在下载");
        //漫游
        dmRequest.setAllowedOverRoaming(false);

        if(isForceUpdate) {
            dmRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            dmRequest.setVisibleInDownloadsUi(false);
        }

        return dm.enqueue(dmRequest);

    }
}
