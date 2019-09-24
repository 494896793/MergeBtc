package com.bochat.app.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 11:00
 * Description :
 */

public class BoChatItemView extends RelativeLayout {
    
    private Context context;
    private View layout;
    private TextView textView;
    private TextView contentView;
    private TextView LeftcontentView;
    private ImageView rightBtn;
    private ImageView rightIcon;
    private TextView rightPadding;
    private EditText editText;
    private SwitchCompat switchBtn;

    public BoChatItemView(Context context) {
        this(context, null);
    }

    public BoChatItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoChatItemView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        layout = LayoutInflater.from(context).inflate(R.layout.view_item_view, null);
        textView = layout.findViewById(R.id.bochat_item_text);
        contentView = layout.findViewById(R.id.bochat_item_content);
        LeftcontentView = layout.findViewById(R.id.bochat_item_left_content);
        rightBtn = layout.findViewById(R.id.bochat_item_btn);
        rightIcon = layout.findViewById(R.id.bochat_item_right_icon);
        switchBtn = layout.findViewById(R.id.bochat_item_switch);
        rightPadding = layout.findViewById(R.id.bochat_item_right_padding);
        editText = layout.findViewById(R.id.bochat_item_edit);
        addView(layout);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BoChatItemView, defStyleAttr, 0);
        if (array != null) {
            if(array.hasValue(R.styleable.BoChatItemView_item_text)){
                String titleText = array.getString(R.styleable.BoChatItemView_item_text);
                textView.setText(titleText);
            }

            if(array.hasValue(R.styleable.BoChatItemView_item_text_color)){
                String textColor = array.getString(R.styleable.BoChatItemView_item_text_color);
                textView.setTextColor(Color.parseColor(textColor));
            }

            if(array.hasValue(R.styleable.BoChatItemView_item_content)){
                String titleText = array.getString(R.styleable.BoChatItemView_item_content);
                contentView.setText(titleText);
                LeftcontentView.setText(titleText);
            }

            if(array.hasValue(R.styleable.BoChatItemView_item_content_color)){
                String titleTextColor = array.getString(R.styleable.BoChatItemView_item_content_color);
                contentView.setTextColor(Color.parseColor(titleTextColor));
            }

            if(array.hasValue(R.styleable.BoChatItemView_item_rightIcon)){
                int rightIconId = array.getResourceId(R.styleable.BoChatItemView_item_rightIcon, 0);
                if(rightIconId != 0){
                    rightIcon.setVisibility(VISIBLE);
                    rightIcon.setImageResource(rightIconId);
                }
            }
            if(array.hasValue(R.styleable.BoChatItemView_item_hasRightBtn)){
                boolean hasBackBtn = array.getBoolean(R.styleable.BoChatItemView_item_hasRightBtn, true);
                if(hasBackBtn){
                    rightBtn.setVisibility(View.VISIBLE);
                    rightPadding.setVisibility(View.GONE);
                } else {
                    rightBtn.setVisibility(View.GONE);
                    rightPadding.setVisibility(View.VISIBLE);
                }
            }
            if(array.hasValue(R.styleable.BoChatItemView_item_hasSwitchBtn)){
                boolean hasBackBtn = array.getBoolean(R.styleable.BoChatItemView_item_hasSwitchBtn, false);
                if(hasBackBtn){
                    switchBtn.setVisibility(View.VISIBLE);
                    rightPadding.setVisibility(View.GONE);
                } else {
                    switchBtn.setVisibility(View.GONE);
                    rightPadding.setVisibility(View.VISIBLE);
                }
            }
            if(array.hasValue(R.styleable.BoChatItemView_item_hasEditText)){
                boolean hasEditText = array.getBoolean(R.styleable.BoChatItemView_item_hasEditText, false);
                if(hasEditText){
                    contentView.setVisibility(View.GONE);
                    editText.setVisibility(View.VISIBLE);
                } else {
                    editText.setVisibility(View.GONE);
                }
            }
            if(array.hasValue(R.styleable.BoChatItemView_item_editTextEnable)){
                boolean editTextEnable = array.getBoolean(R.styleable.BoChatItemView_item_editTextEnable, false);
                editText.setEnabled(editTextEnable);
                editText.setFocusable(editTextEnable);
                editText.setClickable(editTextEnable);
            }
            if(array.hasValue(R.styleable.BoChatItemView_item_editTextHint)){
                String editTextHint = array.getString(R.styleable.BoChatItemView_item_editTextHint);
                if(!TextUtils.isEmpty(editTextHint)){
                    editText.setHint(editTextHint);
                }
            }

            if (array.hasValue(R.styleable.BoChatItemView_item_editTextHintColor)){
                String editTextColor = array.getString(R.styleable.BoChatItemView_item_editTextHintColor);
                editText.setHintTextColor(Color.parseColor(editTextColor));
            }

            if (array.hasValue(R.styleable.BoChatItemView_item_editTextNumber)){
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            if(array.hasValue(R.styleable.BoChatItemView_item_hasLeftContent)){
                boolean hasLeftContent = array.getBoolean(R.styleable.BoChatItemView_item_hasLeftContent, false);
                if(hasLeftContent){
                    contentView.setVisibility(View.GONE);
                    LeftcontentView.setVisibility(View.VISIBLE);
                } else {
                    LeftcontentView.setVisibility(View.GONE);
                }
            }

            if(array.hasValue(R.styleable.BoChatItemView_item_leftContentColor)){
                String textColor = array.getString(R.styleable.BoChatItemView_item_leftContentColor);
                LeftcontentView.setTextColor(Color.parseColor(textColor));
            }



            array.recycle();
        }
    }

    public TextView getTextView() {
        return textView;
    }
    
    public TextView getEditText() {
        return editText;
    }
    
    public TextView getLeftcontentView() {
        return LeftcontentView;
    }


    public TextView getContentView() {
        return contentView;
    }

    public ImageView getRightBtn() {
        return rightBtn;
    }

    public SwitchCompat getSwitchBtn() {
        return switchBtn;
    }
}