package com.bochat.app.app.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/12 18:34
 * Description :
 */

public class PayPasswordDialog extends BasePopupWindow implements View.OnClickListener {

    private OnEnterListener onEnterListener;
    
    private Button exitBtn;
    
    private TextView tipsView;
    
    private TextView input1;
    private TextView input2;
    private TextView input3;
    private TextView input4;
    private TextView input5;
    private TextView input6;
    
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private RelativeLayout btnBack;

    private String password = "";
    
    private View view;
    
    public PayPasswordDialog(Context context, String tips) {
        super(context);
        setPopupGravity(Gravity.BOTTOM | Gravity.LEFT);
        
        exitBtn = findViewById(R.id.pay_password_dialog_exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
                
        input1 = findViewById(R.id.pay_password_input_1);
        input2 = findViewById(R.id.pay_password_input_2);
        input3 = findViewById(R.id.pay_password_input_3);
        input4 = findViewById(R.id.pay_password_input_4);
        input5 = findViewById(R.id.pay_password_input_5);
        input6 = findViewById(R.id.pay_password_input_6);
        btn0 = findViewById(R.id.pay_password_btn_0);
        btn1 = findViewById(R.id.pay_password_btn_1);
        btn2 = findViewById(R.id.pay_password_btn_2);
        btn3 = findViewById(R.id.pay_password_btn_3);
        btn4 = findViewById(R.id.pay_password_btn_4);
        btn5 = findViewById(R.id.pay_password_btn_5);
        btn6 = findViewById(R.id.pay_password_btn_6);
        btn7 = findViewById(R.id.pay_password_btn_7);
        btn8 = findViewById(R.id.pay_password_btn_8);
        btn9 = findViewById(R.id.pay_password_btn_9);
        btnBack = findViewById(R.id.pay_password_btn_back);

        tipsView = findViewById(R.id.pay_password_dialog_amount);
        tipsView.setText(tips);
        
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        
    }

    private void updateInputCount(int count){
        input1.setText(count >= 1 ? "*" : "");
        input2.setText(count >= 2 ? "*" : "");
        input3.setText(count >= 3 ? "*" : "");
        input4.setText(count >= 4 ? "*" : "");
        input5.setText(count >= 5 ? "*" : "");
        input6.setText(count >= 6 ? "*" : "");
    }
    
    @Override
    public View onCreateContentView() {
        view = createPopupById(R.layout.view_pay_password_dialog);
        return view;
    }
    
    public void setOnEnterListener(OnEnterListener onEnterListener){
        this.onEnterListener = onEnterListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_password_btn_0:
                password += "0";
            break;
            case R.id.pay_password_btn_1:
                password += "1";
            break;
            case R.id.pay_password_btn_2:
                password += "2";
            break;
            case R.id.pay_password_btn_3:
                password += "3";
            break;
            case R.id.pay_password_btn_4:
                password += "4";
            break;
            case R.id.pay_password_btn_5:
                password += "5";
            break;
            case R.id.pay_password_btn_6:
                password += "6";
            break;
            case R.id.pay_password_btn_7:
                password += "7";
            break;
            case R.id.pay_password_btn_8:
                password += "8";
            break;
            case R.id.pay_password_btn_9:
                password += "9";
            break;
            case R.id.pay_password_btn_back:
                if(password.length() >= 1){
                    password = password.substring(0, password.length() - 1);
                }
            break;
        
            default:
                break;
        }
        updateInputCount(password.length());
        if(password.length() >= 6){
            if(onEnterListener != null){
                onEnterListener.onEnter(password);
            }
            dismiss();
        }
    }
    
    @Override
    public void showPopupWindow() {
        getPopupWindow().showAtLocation(view, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
    }

    public interface OnEnterListener {
        public void onEnter(String password);
    }
}
