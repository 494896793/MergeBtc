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
public class PhotoPlugin implements IPluginModule {

    private OnPhotoBtClickListenner onPhotoBtClickListenner;

    public void setOnPhotoBtClickListenner(OnPhotoBtClickListenner onPhotoBtClickListenner){
        this.onPhotoBtClickListenner=onPhotoBtClickListenner;
    }

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.mipmap.communicate_photo_icon);
    }

    @Override
    public String obtainTitle(Context context) {
        return "图片";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        if(onPhotoBtClickListenner!=null){
            onPhotoBtClickListenner.onPhotoClick();
        }
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }


    public interface OnPhotoBtClickListenner{
        void onPhotoClick();
    }

}
