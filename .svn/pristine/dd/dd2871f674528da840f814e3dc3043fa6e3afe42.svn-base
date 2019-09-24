package com.bochat.app.model.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.bochat.app.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

/**
 * 2019/5/6
 * Author LDL
 **/
public class MyPlugin implements IPluginModule {

    OnRedPacketClick onRedPacketClick;

    public void setOnRedPacketClick(OnRedPacketClick onRedPacketClick){
        this.onRedPacketClick=onRedPacketClick;
    }

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.mipmap.redpacket_bt_img);
    }

    @Override
    public String obtainTitle(Context context) {
        return "利是";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        if(onRedPacketClick!=null){
            onRedPacketClick.onRedPacketOnclick();
        }
    }



    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }



    public interface OnRedPacketClick{
        void onRedPacketOnclick();
    }

}
