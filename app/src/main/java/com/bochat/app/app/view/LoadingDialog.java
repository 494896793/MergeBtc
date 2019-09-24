package com.bochat.app.app.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.bochat.app.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/16 19:19
 * Description :
 */

public class LoadingDialog extends BasePopupWindow {
    
    public LoadingDialog(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        setBackground(0);
        setAllowDismissWhenTouchOutside(false);
        setPopupFadeEnable(true);
    }
    
    
    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.view_loading_dialog);
    }
    
    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
    }
}