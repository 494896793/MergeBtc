package com.bochat.app.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;


/******
 ** author:ldl
 ** time:2018/12/25
 ******/
public class NoticeDialog extends Dialog implements View.OnClickListener {

    private TextView title;
    private TextView msg;
    private TextView submit_bt;
    private TextView cancel_bt;
    private OnButtonclick onButtonclick;

    public NoticeDialog(Context context) {
        super(context);
    }

    public void setOnButtonclick(OnButtonclick onButtonclick){
        this.onButtonclick=onButtonclick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_dialog);

        title=findViewById(R.id.title);
        msg=findViewById(R.id.msg);
        submit_bt=findViewById(R.id.submit_bt);
        cancel_bt=findViewById(R.id.cancel_bt);
        cancel_bt.setOnClickListener(this);
        submit_bt.setOnClickListener(this);

        getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //这句就是设置dialog横向满屏了。
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity=Gravity.CENTER;
        getWindow().setAttributes(lp);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ((RelativeLayout)findViewById(R.id.mix_panel)).getBackground().setAlpha(205);
    }

    public void setMsg(String msg){
        this.msg.setText(msg);
    }

    public void setTitle(String msg){
        this.title.setText(msg);
    }

    public void setLeftButtonMsg(String msg){
        submit_bt.setText(msg);
    }

    public void setRightButtonMsg(String msg){
        cancel_bt.setText(msg);
    }

    @Override
    public void onClick(View v) {
        if(onButtonclick!=null){
            switch (v.getId()){
                case R.id.submit_bt:
                    onButtonclick.onSubmitBtClick(v);
                    break;
                case R.id.cancel_bt:
                    onButtonclick.onCancelBtClick(v);
                    break;
            }
        }
    }

    public interface OnButtonclick{
        void onSubmitBtClick(View view);
        void onCancelBtClick(View view);
    }

}
