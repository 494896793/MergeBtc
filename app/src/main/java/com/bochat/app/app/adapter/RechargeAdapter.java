package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.model.bean.RechargeTypeEntity;

import java.util.List;

/**
 * 2019/7/17
 * Author LDL
 **/
public class RechargeAdapter extends BaseAdapter {

    private Context context;
    private List<RechargeTypeEntity> list;

    public RechargeAdapter(Context context,List<RechargeTypeEntity> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void refreshData(List<RechargeTypeEntity> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_recharge_item,null);
        ImageView choose_img=view.findViewById(R.id.choose_img);
        TextView name_label=view.findViewById(R.id.name_label);
        TextView desc_label=view.findViewById(R.id.desc_label);
        name_label.setText(list.get(position).getPrice()+"元");
        desc_label.setText(list.get(position).getExpireDate()+"体验");
        if(position==0){
            choose_img.setVisibility(View.VISIBLE);
            initChooseColor(view,true);
        }else{
            initChooseColor(view,false);
        }
        return view;
    }

    private void initChooseColor(View view,boolean isChoose){
        if(isChoose){
            ((TextView)view.findViewById(R.id.desc_label)).setTextColor(context.getResources().getColor(R.color.vip_text_choose));
            ((TextView)view.findViewById(R.id.name_label)).setTextColor(context.getResources().getColor(R.color.vip_text_choose));
            view.findViewById(R.id.choose_img).setVisibility(View.VISIBLE);
        }else{
            view.findViewById(R.id.choose_img).setVisibility(View.GONE);
            ((TextView)view.findViewById(R.id.name_label)).setTextColor(context.getResources().getColor(R.color.color_222222));
            ((TextView)view.findViewById(R.id.desc_label)).setTextColor(context.getResources().getColor(R.color.color_999999));
        }
    }


}
