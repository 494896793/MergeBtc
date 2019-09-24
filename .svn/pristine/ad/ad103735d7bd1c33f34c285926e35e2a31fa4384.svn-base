package com.bochat.app.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;

/******
 ** author:ldl
 ** time:2018/12/25
 ******/
public class NoticeAppDialog extends Dialog implements View.OnClickListener {

    private OnButtonclick onButtonclick;
    private LinearLayout above_linear;
    private TextView cancel_bt;
    private TextView second_bt;
    private TextView first_bt;


    public NoticeAppDialog(Context context) {
        super(context);
    }

    public void setOnButtonclick(OnButtonclick onButtonclick){
        this.onButtonclick=onButtonclick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apple_dialog);

        first_bt=findViewById(R.id.first_bt);
        second_bt=findViewById(R.id.second_bt);
        cancel_bt=findViewById(R.id.cancel_bt);
        above_linear=findViewById(R.id.above_linear);

        first_bt.setOnClickListener(this);
        second_bt.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);

//        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //这句就是设置dialog横向满屏了。
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity=Gravity.BOTTOM;
        getWindow().setAttributes(lp);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        above_linear.getBackground().setAlpha(190);
        cancel_bt.getBackground().setAlpha(190);
        ((LinearLayout)findViewById(R.id.mix_panel)).getBackground().setAlpha(205);
    }


    @Override
    public void onClick(View v) {
        if(onButtonclick!=null){
            switch (v.getId()){
                case R.id.first_bt:
                    onButtonclick.onFirstButtonClick(v);
                    break;
                case R.id.second_bt:
                    onButtonclick.onSecondButtonClick(v);
                    break;
                case R.id.cancel_bt:
                    onButtonclick.onCancelButtonClick(v);
                    break;
            }
        }
    }

    public interface OnButtonclick{
        void onFirstButtonClick(View view);
        void onSecondButtonClick(View view);
        void onCancelButtonClick(View view);
    }

}
