package com.bochat.app.app.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bochat.app.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 17:31
 * Description :
 */

public class MessageDialog extends BasePopupWindow {
    
    private OnChooseListener onClickListener;
    private TextView titleText;
    private TextView enterBtn;
    private TextView cancelBtn;
    private TextView contentText;
    
    public MessageDialog(Context context, String title){
        this(context, title, title);
    }
    
    public MessageDialog(Context context, String title, String content) {
        super(context);
        setPopupGravity(Gravity.BOTTOM | Gravity.LEFT);
        setBackground(0);
        titleText = findViewById(R.id.message_dialog_title);
        titleText.setText(title);
        contentText = findViewById(R.id.message_dialog_content);
        contentText.setText(content);
        enterBtn = findViewById(R.id.message_dialog_enter_btn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onClickListener != null){
                    onClickListener.onEnter();
                }
            }
        });
        cancelBtn = findViewById(R.id.message_dialog_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onClickListener != null){
                    onClickListener.onCancel();
                }
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.view_message_dialog);
    }
    
    public void setOnChooseListener(OnChooseListener onClickListener){
        this.onClickListener = onClickListener;
    }
    
    public interface OnChooseListener {
        public void onEnter();
        public void onCancel();
    }
}
