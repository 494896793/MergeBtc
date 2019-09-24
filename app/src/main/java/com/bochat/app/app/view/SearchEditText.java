package com.bochat.app.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/18 14:09
 * Description :
 */

public class SearchEditText extends RelativeLayout {

    private View layout;
    private EditText edittext;
    private Button clearBtn;
    private TextView cancelBtn;
    private TextInputEnterListener textInputEnterListener;
    
    public SearchEditText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        layout = LayoutInflater.from(context).inflate(R.layout.view_search_edittext, null);
        edittext = layout.findViewById(R.id.search_edittext);
        clearBtn = layout.findViewById(R.id.search_clear_btn);
        cancelBtn = layout.findViewById(R.id.search_cancel_btn);
        clearBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edittext.getEditableText().clear();
            }
        });
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textInputEnterListener != null){
                    textInputEnterListener.onCancel();
                }
            }
        });
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    clearBtn.setVisibility(VISIBLE);
                } else {
                    clearBtn.setVisibility(INVISIBLE);
                }
                if(textInputEnterListener != null){
                    textInputEnterListener.onTextChange(edittext.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edittext.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //TODO 回调两次问题
                if(event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                    if(textInputEnterListener != null){
                        textInputEnterListener.onEnter(edittext.getText().toString());
                    }
                }
                return false;
            }
        });
        addView(layout);
    
        if(attrs != null){
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchEditText, defStyleAttr, 0);
            if (array != null) {
                if(array.hasValue(R.styleable.SearchEditText_searchHint)){
                    String titleText = array.getString(R.styleable.SearchEditText_searchHint);
                    edittext.setHint(titleText);
                }
                array.recycle();
            }
        }
    }
    
    public void setTextInputEnterListener(TextInputEnterListener  listener){
        textInputEnterListener = listener;
    }

    public void setHint(String text){
        edittext.setHint(text);
    }
    
    public interface TextInputEnterListener {
        public void onTextChange(String text);
        public void onEnter(String text);
        public void onCancel();
    }
}
