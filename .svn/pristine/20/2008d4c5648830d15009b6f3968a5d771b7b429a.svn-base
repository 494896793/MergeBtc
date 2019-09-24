package com.bochat.app.model.rong;

import android.util.Log;

import com.bochat.app.model.event.ConversationRefreshEvent;

import org.greenrobot.eventbus.EventBus;

import io.rong.imlib.RongIMClient;

/**
 * 2019/5/27
 * Author LDL
 **/
public class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

        switch (connectionStatus){

            case CONNECTED://连接成功。
                EventBus.getDefault().post(new ConversationRefreshEvent());
                Log.i("+++++++++++++++","======================连接成功");
                break;
            case DISCONNECTED://断开连接。
                Log.i("+++++++++++++++","======================断开连接");
                break;
            case CONNECTING://连接中。
                Log.i("+++++++++++++++","======================连接中");
                break;
            case NETWORK_UNAVAILABLE://网络不可用。
                Log.i("+++++++++++++++","======================网络不可用");
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                Log.i("+++++++++++++++","======================用户账户在其他设备登录，本机会被踢掉线");
                break;
        }
    }

}
