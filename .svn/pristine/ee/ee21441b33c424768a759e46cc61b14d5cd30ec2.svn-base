package com.bochat.app.model.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.bochat.app.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

/**
 * 2019/5/15
 * Author LDL
 **/
public class SpeedConverPlugin implements IPluginModule {


    private OnSpeedConverBtClickListenner onSpeedConverBtClickListenner;

    public void setOnSpeedConverBtClickListenner(OnSpeedConverBtClickListenner onSpeedConverBtClickListenner){
        this.onSpeedConverBtClickListenner=onSpeedConverBtClickListenner;
    }

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.mipmap.communicate_speedconver_icon);
    }

    @Override
    public String obtainTitle(Context context) {
        return "闪兑";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        if(onSpeedConverBtClickListenner!=null){
            onSpeedConverBtClickListenner.onSpeedConverClick();
        }
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }

    public interface OnSpeedConverBtClickListenner{
        void onSpeedConverClick();
    }

}
