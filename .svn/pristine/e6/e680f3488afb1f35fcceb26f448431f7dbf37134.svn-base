package com.bochat.app.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bochat.app.R;

/**
 * Created by ggyy on 2019/6/9 ${Month}.
 */

public class RechargeRadioButtonView extends LinearLayout{

    private Context context;

    private ImageView radioImage;
    private ImageView payIcon;
    private TextView textView;
    private int index = 0;
    private int id = 0;// 判断是否选中
    private RechargeRadioButtonView tempRadioButton;// 模版用于保存上次点击的对象



    private int state[] = { R.mipmap.administrators_choice_sel, R.mipmap.administrators_choice_sel };

    public RechargeRadioButtonView(Context context) {
        this(context, null);
    }

    public RechargeRadioButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.item_recharge, this, true);

        radioImage = (ImageView) findViewById(R.id.recharge_type_radio);
        payIcon = (ImageView) findViewById(R.id.recharge_type_icon);
        textView = (TextView) findViewById(R.id.recharge_type_name);
    }

    public RechargeRadioButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /***

     * 改变图片

     */

    public void ChageRadioImage() {

        index++;

        id = index % 2;// 获取图片id

        radioImage.setImageResource(state[id]);

    }

   //设置文本

    public void setText(String text) {

        textView.setText(text);

    }
    public String getText() {

        return id == 0 ? "" : textView.getText().toString();

    }



    /*
     * 设置图片
     */
    public void setRadioImage(Drawable drawable) {

        payIcon.setImageDrawable(drawable);

    }



}
