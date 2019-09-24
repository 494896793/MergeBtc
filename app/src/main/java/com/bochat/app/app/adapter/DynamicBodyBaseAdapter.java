package com.bochat.app.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.common.contract.dynamic.DynamicContract;
import com.bochat.app.model.bean.DynamicTopShopEntity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 2019/5/8
 * Author LDL
 **/
public class DynamicBodyBaseAdapter extends BaseAdapter implements View.OnClickListener {

    private List<DynamicTopShopEntity> data;
    private Context mContext;
    private OnItemClick onItemClick;
    private int types;

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick=onItemClick;
    }

    public DynamicBodyBaseAdapter(List<DynamicTopShopEntity> data,Context mContext,int types){
        this.data=data;
        this.mContext=mContext;
        this.types=types;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.dynamic_body_baseadapter_item,null);
        view.setTag(position+"");
        TextView app_title=view.findViewById(R.id.app_title);
        TextView app_desc=view.findViewById(R.id.app_desc);
        ImageView imageView=view.findViewById(R.id.app_img);
        TextView circle_red=view.findViewById(R.id.circle_red);
        app_title.setText(data.get(position).getName());
        if(data.get(position).getName().equals(mContext.getString(R.string.dynamic_activity))){
            circle_red.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(data.get(position).getDesc())){
            app_desc.setText(data.get(position).getDescribes());
        }else{
            app_desc.setText(data.get(position).getDesc());
        }
        if(TextUtils.isEmpty(data.get(position).getImage())){
            imageView.setImageResource(data.get(position).getImgRecourseId());
        }else{
            Glide.with(mContext).load(data.get(position).getImage()).into(imageView);
        }
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(onItemClick!=null){
            onItemClick.onClick(Integer.valueOf(v.getTag().toString()),data.get(Integer.valueOf(v.getTag().toString())));
        }
    }

    interface OnItemClick{
        void onClick(int position,DynamicTopShopEntity entity);
    }

}