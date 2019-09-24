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
public class CameraPlugin implements IPluginModule {

    private OnCameraBtClickListenner onCameraBtClickListenner;


    public void setOnCameraBtClickListenner(OnCameraBtClickListenner onCameraBtClickListenner){
        this.onCameraBtClickListenner=onCameraBtClickListenner;
    }


    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.mipmap.communicate_camera_icon);
    }

    @Override
    public String obtainTitle(Context context) {
        return "拍摄";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        if(onCameraBtClickListenner!=null){
            onCameraBtClickListenner.onCameraClick();
        }
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }

    public interface OnCameraBtClickListenner{
        void onCameraClick();
    }

}
