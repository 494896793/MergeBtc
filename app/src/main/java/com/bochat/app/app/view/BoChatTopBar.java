package com.bochat.app.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.mvp.view.BaseActivity;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/11 14:06
 * Description :
 */

public class BoChatTopBar extends RelativeLayout {
    
    private TextView titleTextView;
    private RelativeLayout backBtnLayout;
    private RelativeLayout extBtnLayout;
    private RelativeLayout extBtn2Layout;
    private TextView extBtn;
    private TextView extBtn2;
    private TextView extBtn3;

    private OnClickListener clickListener;

    public BoChatTopBar(Context context) {
        this(context, null);
    }

    public BoChatTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoChatTopBar(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        View layout = LayoutInflater.from(context).inflate(R.layout.view_top_bar, null);
        titleTextView = layout.findViewById(R.id.top_bar_title);
        TextView backBtn = layout.findViewById(R.id.top_bar_back_btn);
        extBtn = layout.findViewById(R.id.top_bar_ext_btn);
        extBtn2 = layout.findViewById(R.id.top_bar_ext_btn_2);
        extBtn3 = layout.findViewById(R.id.top_bar_ext_btn_3);
        backBtnLayout = layout.findViewById(R.id.top_bar_back_layout);
        extBtnLayout = layout.findViewById(R.id.top_bar_ext_btn_layout);
        extBtn2Layout = layout.findViewById(R.id.top_bar_ext_btn_2_layout);
        
        addView(layout);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BoChatTopBar, defStyleAttr, 0);
        if (array != null) {
            if(array.hasValue(R.styleable.BoChatTopBar_top_titleText)){
                String titleText = array.getString(R.styleable.BoChatTopBar_top_titleText);
                titleTextView.setText(titleText);
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_titleColor)){
                titleTextView.setTextColor(Color.parseColor(array.getString(R.styleable.BoChatTopBar_top_titleColor)));
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_hasBackBtn)){
                boolean hasBackBtn = array.getBoolean(R.styleable.BoChatTopBar_top_hasBackBtn, false);
                if(hasBackBtn){
                    backBtnLayout.setVisibility(View.VISIBLE);
                }
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_hasExtBtn)){
                boolean hasExtBtn = array.getBoolean(R.styleable.BoChatTopBar_top_hasExtBtn, false);
                if(hasExtBtn){
                    extBtnLayout.setVisibility(View.VISIBLE);
                }
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_hasExt2Btn)){
                boolean hasExt2Btn = array.getBoolean(R.styleable.BoChatTopBar_top_hasExt2Btn, false);
                if(hasExt2Btn){
                    extBtn2Layout.setVisibility(View.VISIBLE);
                }
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_hasExt3Btn)){
                boolean hasExt3Btn = array.getBoolean(R.styleable.BoChatTopBar_top_hasExt3Btn, false);
                if(hasExt3Btn){
                    extBtn3.setVisibility(View.VISIBLE);
                }
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_textColor)){
                int textColor = array.getColor(R.styleable.BoChatTopBar_top_textColor, getContext().getResources().getColor(R.color.top_bar_text));
                titleTextView.setTextColor(textColor);
            }

            if(array.hasValue(R.styleable.BoChatTopBar_top_background)){
                int backgroundColor = array.getColor(R.styleable.BoChatTopBar_top_background, getContext().getResources().getColor(R.color.top_bar_bg));
                layout.setBackgroundColor(backgroundColor);
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_extButtonSrc)){
                int extBtn1SrcId = array.getResourceId(R.styleable.BoChatTopBar_top_extButtonSrc, R.mipmap.ic_chat_shandui);
                extBtn.setBackground(getResources().getDrawable(extBtn1SrcId));
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_extButton2Src)){
                int extBtn2SrcId = array.getResourceId(R.styleable.BoChatTopBar_top_extButton2Src, R.mipmap.ic_chat_setting);
                extBtn2.setBackground(getResources().getDrawable(extBtn2SrcId));
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_extButton3Text)){
                String extBtn3SrcText = array.getString(R.styleable.BoChatTopBar_top_extButton3Text);
                extBtn3.setText(extBtn3SrcText);
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_extButton3TextColor)){
                int textColor = array.getColor(R.styleable.BoChatTopBar_top_extButton3TextColor, getContext().getResources().getColor(R.color.top_bar_text));
                extBtn3.setTextColor(textColor);
            }
            if(array.hasValue(R.styleable.BoChatTopBar_top_backBtnBackground)){
                int id = array.getResourceId(R.styleable.BoChatTopBar_top_backBtnBackground, R.mipmap.ic_chat_setting);
                backBtn.setBackground(getResources().getDrawable(id));
            }
            array.recycle();

            backBtnLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener != null && clickListener.onBackButtonClick()){
                        return;
                    }
                    if(context instanceof BaseActivity){
                        ((BaseActivity) context).finish();
                        if (clickListener!= null){
                            clickListener.onActivityFinish();
                        }

                    }
                }
            });
        }
    }

    public void setRightText(String text, View.OnClickListener onClickListener){
        extBtn3.setText(text);
        if(onClickListener!=null){
            extBtn3.setOnClickListener(onClickListener);
        }
    }
    
    public void setReturnBtVisible(boolean isVisible){
        if(isVisible){
            backBtnLayout.setVisibility(VISIBLE);
        }
    }

    public void setReturnBtListenner(View.OnClickListener onClickListener){
        backBtnLayout.setOnClickListener(onClickListener);
    }

    public void setTitleText(String msg){
        titleTextView.setText(msg);
    }

    public void setRightButtonInvisble(boolean isHide){
        if(isHide){
            extBtn.setVisibility(GONE);
            extBtn2.setVisibility(GONE);
        }
    }

    public TextView getTextButton(){
        return extBtn3;
    }

    public TextView getExtBtn() {
        return extBtn;
    }

    public TextView getExtBtn2() {
        return extBtn2;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setOnClickListener(final OnClickListener onClickListener){
        clickListener = onClickListener;
        extBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onLeftExtButtonClick();
            }
        });
        extBtn2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onRightExtButtonClick();
            }
        });
        extBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onTextExtButtonClick();
            }
        });
    }

    public static abstract class OnClickListener{
        public void onLeftExtButtonClick(){

        }
        public void onRightExtButtonClick(){

        }
        public void onTextExtButtonClick(){

        }
        public boolean onBackButtonClick(){
            return false;
        }
        public void onActivityFinish(){}
    }
}