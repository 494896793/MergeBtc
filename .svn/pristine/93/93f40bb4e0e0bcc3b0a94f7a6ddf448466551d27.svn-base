package com.bochat.app.app.view;

/**
 * Created by ggyy on 2019/6/8 ${Month}.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bochat.app.R;


public class CommandDailog extends Dialog {
    private Button yes;
    private Button no;
    private TextView message;
    private String titleStr;
    private String messageStr;
    private String yesStr, noStr;
    private onNoOnclickListener noOnclickListener;
    private onYesOnclickListener yesOnclickListener;

    public CommandDailog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    //设置取消按钮的显示内容和监听
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听

     */
    public void setYesOnclickListener(String str, onYesOnclickListener yesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = yesOnclickListener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_command_dialog);
        //空白处不能取消动画
        setCanceledOnTouchOutside(true);

        //初始化界面控件
        initView();

        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = findViewById(com.bochat.app.R.id.yes);
        no = findViewById(com.bochat.app.R.id.no);
        message = (TextView) findViewById(com.bochat.app.R.id.message);
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //自定了message

        if (messageStr != null) {
            message.setText(messageStr);
        }
        //如果设置按钮文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
    }

    /**
     * 初始化界面的确定和取消监听
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesOnclick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }

    
    /**
     * 从外界Activity为Dialog设置message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    public interface onNoOnclickListener {
         void onNoClick();
    }

    public interface onYesOnclickListener {
         void onYesOnclick();
    }
}