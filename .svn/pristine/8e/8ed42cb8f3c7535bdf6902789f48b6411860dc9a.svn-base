package com.bochat.app.app.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.router.RouterRechargeVip;
import com.bochat.app.model.bean.ShakyCandyEntity;
import com.bochat.app.model.bean.VipStatuEntity;
import com.bochat.app.model.event.RedHallEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 2019/6/28
 * Author LDL
 **/
public class VipRedDialog extends Dialog {

    @BindView(R.id.coin_close)
    Button coin_close;

    @BindView(R.id.go_vip_text)
    TextView go_vip_text;

    @BindView(R.id.relative)
    RelativeLayout relative;

    VipStatuEntity vipStatuEntity;
    private Context context;

    public VipRedDialog(Context context, VipStatuEntity vipStatuEntity) {
        super(context,R.style.TransparentDialog);
        this.context=context;
        this.vipStatuEntity=vipStatuEntity;
    }

    public VipRedDialog(Context context, int themeResId) {
        super(context,R.style.TransparentDialog);
        this.context=context;
    }

    @Override
    public void show() {
        super.show();
        if(vipStatuEntity!=null){
            if(vipStatuEntity.getIsOpen().equals("2")){     //已过期
                relative.setBackgroundResource(R.mipmap.expire_bg);
            }else{
                relative.setBackgroundResource(R.mipmap.experience_bg);
            }
        }
    }



    protected VipRedDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context,R.style.TransparentDialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_vipred);
        ButterKnife.bind(this);
        setStyle();
    }

    @OnClick({R.id.coin_close,R.id.go_vip_text})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.coin_close:
                dismiss();
                EventBus.getDefault().post(new RedHallEvent());
                break;
            case R.id.go_vip_text:
                Router.navigation(new RouterRechargeVip(vipStatuEntity));
                dismiss();
                break;
        }
    }

    /**
     * 设置样式
     */
    private void setStyle() {
        WindowManager.LayoutParams layoutParams;
        if (getWindow() != null) {
            layoutParams = getWindow().getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.width= context.getResources().getDimensionPixelSize(R.dimen.dp_270);
            getWindow().setAttributes(layoutParams);
//            getWindow().setWindowAnimations(R.style.mystyle);
        }
    }
}
