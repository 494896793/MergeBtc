package com.bochat.app.model.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bochat.app.MainApplication;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.util.APKUtils;
import com.bochat.app.model.util.NotificationUtils;

import static com.bochat.app.model.constant.Constan.APK_NAME;
import static com.bochat.app.model.constant.Constan.DIRPATH;
import static com.bochat.app.model.constant.Constan.DOWN_ACTION_DOWN_STOP;
import static com.bochat.app.model.constant.Constan.SOON_FILE_DIR;

/**
 * 2019/5/10
 * Author LDL
 **/
public class DownReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(action.equals(Constan.DOWN_ACTION_STAR)){
            Log.i("=====","=====开始下载");
            NotificationUtils.getInstance().notifyNotification(0,0,"开始下载",null,"");
        }else if(action.equals(Constan.DOWN_ACTION_PROGREE)){
            Log.i("=====","=====正在下载"+intent.getIntExtra("downloadSize",-1));
                NotificationUtils.getInstance().notifyNotification(0,0,"正在下载:"+intent.getIntExtra("downloadSize",-1)+"%","");
        }else if(action.equals(DOWN_ACTION_DOWN_STOP)){
            Log.i("=====","=====任务已停止");
        }else{
            Log.i("=====","=====下载完成");
            NotificationUtils.getInstance().notifyNotification(0,0,"下载完成",null,"");
            APKUtils.instant(DIRPATH,SOON_FILE_DIR,APK_NAME, MainApplication.getInstance());
        }
    }
}
