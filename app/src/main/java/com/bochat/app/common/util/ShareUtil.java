package com.bochat.app.common.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.bochat.app.MainApplication;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.nio.ByteBuffer;

public class ShareUtil {

    private IWXAPI wxapi;
    private static ShareUtil instance;

    private Tencent mTencent;
    
    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
    private static final int THUMB_SIZE = 100; //缩略图大小
    private static String appID;

    private ShareUtil(String appID) {
        this.appID = appID;
    }

    public static ShareUtil getInstance() {
        if (instance == null) {
            synchronized (ShareUtil.class) {
                if (instance == null) {
                    if (appID == null) {
                        //获取清单文件中的APP_ID
                        appID = "wx0389afb0bb6b3ae5";
                    }
                    instance = new ShareUtil(appID);
                }
            }
        }
        return instance;
    }

    public void registerWeiXin() {
        wxapi = WXAPIFactory.createWXAPI(MainApplication.getContext(), appID, true);
        wxapi.registerApp(appID);
    }
    public void registerQQ() {
        mTencent = Tencent.createInstance("1108740347", MainApplication.getContext());
    }

    /**
     * 判断是否安装微信
     */
    public boolean isWeiXinAppInstall() {
        if (wxapi == null)
            wxapi = WXAPIFactory.createWXAPI(MainApplication.getContext(), appID);
        if (wxapi.isWXAppInstalled()) {
            return true;
        } else {
            Toast.makeText(MainApplication.getContext(), "微信未安装", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 是否支持分享到朋友圈
     */
    public boolean isWXAppSupportAPI() {
        if (isWeiXinAppInstall()) {
            int wxSdkVersion = wxapi.getWXAppSupportAPI();
            if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public IWXAPI getWxApi() {
        if (wxapi == null)
            wxapi = WXAPIFactory.createWXAPI(MainApplication.getContext(), appID);
        return wxapi;
    }

    public Tencent getQQApi(){
        if(mTencent == null){
            mTencent = Tencent.createInstance("1108740347", MainApplication.getContext());
        }
        return mTencent;
    }
    
    /**
     * 分享文本类型
     *
     * @param text 文本内容
     * @param type 微信会话或者朋友圈等
     */
    public void shareTextToWx(String text, int type) {
        if (text == null || text.length() == 0) {
            return;
        }
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = type;

        getWxApi().sendReq(req);
    }

    
    public String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    
    /**
     * 分享url地址
     *
     * @param url            地址
     * @param title          标题
     * @param desc           描述
     * @param wxSceneSession 类型
     */
    public void shareUrlToWx(String url, String title, String desc, final int wxSceneSession) {

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desc;
        new Thread(new Runnable() {
            @Override
            public void run() {
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = wxSceneSession;
                getWxApi().sendReq(req);
            }
        }).start();
    }
    
    public void shareImageToWx(final Bitmap bmp, final int scene){
    
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bmp);
        final WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                //设置缩略图
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                bmp.recycle();

                int bytes = thumbBmp.getByteCount();
                ByteBuffer buf = ByteBuffer.allocate(bytes);
                thumbBmp.copyPixelsToBuffer(buf);
                msg.thumbData = buf.array();
                
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message = msg;
                req.scene = scene;
                //调用api接口，发送数据到微信
                getWxApi().sendReq(req);
            }
        }).start();
    }
    
    public void shareToQQ(Activity activity, String url, String title, String desc){
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  desc);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  url);
        mTencent.shareToQQ(activity, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {

            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
