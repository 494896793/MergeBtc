package com.bochat.app.app.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.bochat.app.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/16 19:19
 * Description :
 */

public class AusexDialog extends BasePopupWindow {
    
    public AusexDialog(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        setBackground(0);
        setAllowDismissWhenTouchOutside(false);
        setPopupFadeEnable(true);
    }
    
    
    @Override
    public View onCreateContentView() {

        View popupById = createPopupById(R.layout.view_ausex_dialog);
        Button button = popupById.findViewById(R.id.pop_ausex_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return popupById;
    }
    
    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
    }
}
