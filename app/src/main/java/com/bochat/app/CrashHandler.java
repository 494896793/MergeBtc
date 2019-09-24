package com.bochat.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bochat.app.model.util.LogUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 08:41
 * Description :
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;
    //文件路径
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() +File.separator+ "crash";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFEIX = ".txt";
    private static Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private static CrashHandler mCrashHandler = new CrashHandler();
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return mCrashHandler;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
        looperException();
    }

    private void looperException() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        uncaughtException(null, e);
                    }
                }
            }
        });
    }
    
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //将文件写入sd卡
            writeToSdcard(ex);
            //写入后在这里可以进行上传操作
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtil.LogDebug(ex.getLocalizedMessage());
        ex.printStackTrace();

        Intent intent = new Intent(mContext, CrashHandleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    //将异常写入文件
    private void writeToSdcard(Throwable ex) throws IOException, PackageManager.NameNotFoundException {
        //如果没有SD卡，直接返回
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        File filedir = new File(PATH);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        long currenttime = System.currentTimeMillis();
        String time = String.valueOf(currenttime);
        File exfile = new File(PATH +File.separator+FILE_NAME+time + FILE_NAME_SUFEIX);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(exfile)));
        Log.e("错误日志文件路径",""+exfile.getAbsolutePath());
        pw.println(time);
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        //当前版本号
        pw.println("App Version:" + pi.versionName + "_" + pi.versionCode);
        //当前系统
        pw.println("OS version:" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        //制造商
        pw.println("Vendor:" + Build.MANUFACTURER);
        //手机型号
        pw.println("Model:" + Build.MODEL);
        //CPU架构
        pw.println("CPU ABI:" + Build.CPU_ABI);

        ex.printStackTrace(pw);
        pw.close();

        ex.printStackTrace();
    }
}
