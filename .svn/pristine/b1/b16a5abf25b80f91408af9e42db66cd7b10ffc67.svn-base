package com.bochat.app.model.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bochat.app.R;

import java.util.Locale;

/**
 * 2019/5/10
 * Author LDL
 **/
public class NotificationUtils {

    private static Context mContext;

    private NotificationCompat.Builder builder;

    private NotificationManager manager;

    private static NotificationUtils notificationUtils;

    private int notificationId=0;

    private void initManager(){
        manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static NotificationUtils getInstance(){
        if(notificationUtils==null){
            notificationUtils=new NotificationUtils();
        }
        return notificationUtils;
    }

    public void init(Context mContext){
        this.mContext=mContext;
        initManager();
    }

    public void removeNotification(){
        manager.cancelAll();
    }

    private Notification customNotification(int progress, String text,PendingIntent pendingIntent) {//自定义View通知
        if (builder == null)
            builder = new NotificationCompat.Builder(mContext);

        RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.notification_upgrade);
        view.setProgressBar(R.id.bar, 100, progress, false);
        view.setTextViewText(R.id.tv_des, text);
        view.setTextViewText(R.id.tv_progress, String.format(Locale.getDefault(), "%d%%", progress));
        builder.setCustomContentView(view)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false);
        Notification notification=builder.build();
//        notification.flags=Notification.FLAG_ONGOING_EVENT;
        return notification;
    }

    private Notification customNotification(int progress, String text) {//自定义View通知
        if (builder == null)
            builder = new NotificationCompat.Builder(mContext);

        RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.notification_upgrade);
        view.setProgressBar(R.id.bar, 100, progress, false);
        view.setTextViewText(R.id.tv_des, text);
        view.setTextViewText(R.id.tv_progress, String.format(Locale.getDefault(), "%d%%", progress));
        builder.setCustomContentView(view)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false);
        Notification notification=builder.build();
        return notification;
    }

    public void notifyNotification(int notificationId, int progress, String text,PendingIntent pendingIntent,String tag){
        this.notificationId=notificationId;
        manager.notify(tag,notificationId,customNotification(progress,text,pendingIntent));
    }

    public void notifyNotification(int notificationId, int progress, String text,String tag){
        this.notificationId=notificationId;
        manager.notify(tag,notificationId,customNotification(progress,text));
    }

}
