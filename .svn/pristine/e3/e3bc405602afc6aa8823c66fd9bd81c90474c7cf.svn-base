package com.bochat.app.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.common.util.ShareUtil;
import com.bochat.app.mvp.view.BaseActivity;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 17:31
 * Description :
 */

public class ShareDialog extends BasePopupWindow implements View.OnClickListener {

    private String mTitle;
    private String mDescription;
    private String mUrl;

    private Bitmap mBitmap;
    
    private TextView mShareWechat;
    private TextView mShareWechatFriends;
    private TextView mShareQQ;
    private TextView mShareCancel;

    public ShareDialog(Context context, String title, String description, String url) {
        super(context);
        
        mTitle = title;
        mDescription = description;
        mUrl = url;
        
        init(); 
    }
    
    public ShareDialog(Context context, Bitmap bitmap) {
        super(context);

        mBitmap = bitmap;
        init();
    }

    private void init(){
        setPopupGravity(Gravity.BOTTOM | Gravity.START);

        mShareWechat = findViewById(R.id.share_popup_wechat);
        mShareWechatFriends = findViewById(R.id.share_popup_wechat_friends);
        mShareQQ = findViewById(R.id.share_popup_QQ);
        mShareCancel = findViewById(R.id.share_popup_cancel);

        mShareWechat.setOnClickListener(this);
        mShareWechatFriends.setOnClickListener(this);
        mShareQQ.setOnClickListener(this);
        mShareCancel.setOnClickListener(this);
    }
    
    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.view_share_dialog);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_popup_wechat:
                if(mBitmap != null){
                    ShareUtil.getInstance().shareImageToWx(mBitmap, SendMessageToWX.Req.WXSceneSession);
                } else {
                    ShareUtil.getInstance().shareUrlToWx(mUrl, mTitle, mDescription, SendMessageToWX.Req.WXSceneSession);
                }
                dismiss();
                break;
            case R.id.share_popup_wechat_friends:
                if(mBitmap != null){
                    ShareUtil.getInstance().shareImageToWx(mBitmap, SendMessageToWX.Req.WXSceneTimeline);
                } else {
                    ShareUtil.getInstance().shareUrlToWx(mUrl, mTitle, mDescription, SendMessageToWX.Req.WXSceneTimeline);
                }
                dismiss();
                break;
            case R.id.share_popup_QQ:
                ShareUtil.getInstance().shareToQQ(BaseActivity.getTop(), mUrl, mTitle, mDescription);
                dismiss();
                break;
            case R.id.share_popup_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }
}
