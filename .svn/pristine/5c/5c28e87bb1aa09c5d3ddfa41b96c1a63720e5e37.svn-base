package com.bochat.app.app.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bochat.app.R;

/**
 * Created by ggyy on 2019/6/9 ${Month}.
 */

public class RechargeTypeAdapter extends BaseAdapter {
    private RechargeRadioButtonView temp;
    private Context context;
    private OnPayIiteOnclickListener mPayListener;
    private LayoutInflater inflater;
    private String[] payString = {"支付宝支付"};
    private int[] payIcon = {R.mipmap.wallet_recharge_alipay};


    public RechargeTypeAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final RechargeRadioButtonView radioButton;

        if (convertView == null) {

            radioButton = new RechargeRadioButtonView(context);

        } else {

            radioButton = (RechargeRadioButtonView) convertView;

        }



        radioButton.setText(payString[position]); //改变文字
        radioButton.setRadioImage(getDrawble(payIcon[position]));

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 模版不为空，则chage.
                if (mPayListener != null){
                    mPayListener.payItemOnclick(position);
                }

                if (temp != null) {

                    temp.ChageRadioImage();

                }

                temp = radioButton;
                radioButton.ChageRadioImage(); //改变单选图片

            }
        });


        return radioButton;

    }

    private Drawable getDrawble(int resoureId){
         Drawable drawable = ContextCompat.getDrawable(context, resoureId);
        return drawable;
    }

    public void setOnPayItemClickListener(OnPayIiteOnclickListener listener){
        mPayListener = listener;
    }

    public interface OnPayIiteOnclickListener{
        void payItemOnclick(int type);
    }

}
