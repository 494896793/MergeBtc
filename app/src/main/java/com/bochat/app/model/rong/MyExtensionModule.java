package com.bochat.app.model.rong;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

/**
 * 2019/5/6
 * Author LDL
 **/
public class MyExtensionModule extends DefaultExtensionModule {

    private MyPlugin myPlugin;
    private SpeedConverPlugin speedConverPlugin;
    private CameraPlugin cemarePlugin;
    private PhotoPlugin photoPlugin;

    private OnRedPacketClick onRedPacketClick;
    private OnPhotoBtClickListenner onPhotoBtClickListenner;
    private OnCameraBtClickListenner onCameraBtClickListenner;
    private OnSpeedConverBtClickListenner onSpeedConverBtClickListenner;

    public void setOnPhotoBtClickListenner(OnPhotoBtClickListenner onPhotoBtClickListenner){
        this.onPhotoBtClickListenner=onPhotoBtClickListenner;
    }

    public void setOnCameraBtClickListenner(OnCameraBtClickListenner onCameraBtClickListenner){
        this.onCameraBtClickListenner=onCameraBtClickListenner;
    }

    public void setOnSpeedConverBtClickListenner(OnSpeedConverBtClickListenner onSpeedConverBtClickListenner){
        this.onSpeedConverBtClickListenner=onSpeedConverBtClickListenner;
    }

    public void setOnRedPacketClick(OnRedPacketClick onRedPacketClick){
        this.onRedPacketClick=onRedPacketClick;
    }

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules=new ArrayList<>();
       try{
//           pluginModules =  super.getPluginModules(conversationType);
           speedConverPlugin=new SpeedConverPlugin();
           speedConverPlugin.setOnSpeedConverBtClickListenner(new SpeedConverPlugin.OnSpeedConverBtClickListenner() {
               @Override
               public void onSpeedConverClick() {
                   if(onSpeedConverBtClickListenner!=null){
                       onSpeedConverBtClickListenner.onSpeedConverClick();
                   }
               }
           });

           cemarePlugin=new CameraPlugin();
           cemarePlugin.setOnCameraBtClickListenner(new CameraPlugin.OnCameraBtClickListenner() {
               @Override
               public void onCameraClick() {
                   if(onCameraBtClickListenner!=null){
                       onCameraBtClickListenner.onCameraClick();
                   }
               }
           });

           photoPlugin=new PhotoPlugin();
           photoPlugin.setOnPhotoBtClickListenner(new PhotoPlugin.OnPhotoBtClickListenner() {
               @Override
               public void onPhotoClick() {
                   if(onPhotoBtClickListenner!=null){
                       onPhotoBtClickListenner.onPhotoClick();
                   }
               }
           });

           myPlugin=new MyPlugin();
           myPlugin.setOnRedPacketClick(new MyPlugin.OnRedPacketClick() {
               @Override
               public void onRedPacketOnclick() {
                   if(onRedPacketClick!=null){
                       onRedPacketClick.onRedPacketOnclick();
                   }
               }
           });
           pluginModules.add(photoPlugin);
           pluginModules.add(cemarePlugin);
           pluginModules.add(speedConverPlugin);
           pluginModules.add(myPlugin);
       }catch (Exception e){
           e.printStackTrace();
       }

        return pluginModules;
    }

    public interface OnRedPacketClick{
        void onRedPacketOnclick();
    }

    public interface OnPhotoBtClickListenner{
        void onPhotoClick();
    }

    public interface OnCameraBtClickListenner{
        void onCameraClick();
    }

    public interface OnSpeedConverBtClickListenner{
        void onSpeedConverClick();
    }

}
