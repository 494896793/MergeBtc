package com.bochat.app.model.util.download.remote.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bochat.app.model.util.download.remote.binder.RemoteUpgradeBinder;

import java.util.List;

public class RemoteUpgradeService extends Service {

    public static boolean isConnection = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RemoteUpgradeBinder();
    }

    public static boolean isEqualToProcessPackage(Context context, String target) {
        String pPackage = getProcessPackage(context);
        if (TextUtils.isEmpty(target))
            return false;
        return pPackage != null && pPackage.equals(target);
    }

    public static String getProcessPackage(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }
}
