package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.activity.RedPacketDetailActivity;
import com.bochat.app.model.bean.RedHallListItemEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;

import java.util.List;

/**
 * 2019/7/16
 * Author LDL
 **/
public class RedHallAdapter extends RecyclerView.Adapter<RedHallAdapter.RedHallViewHolder> {


    private Context context;
    private List<RedHallListItemEntity> items;
    private OnRedItemClickListener onRedItemClickListener;

    public RedHallAdapter(Context context,List<RedHallListItemEntity> items){
        this.context=context;
        this.items=items;
    }

    public void setOnRedItemClickListener(OnRedItemClickListener onRedItemClickListener){
        this.onRedItemClickListener=onRedItemClickListener;
    }

    public void refreshData(List<RedHallListItemEntity> items){
        this.items=items;
        notifyDataSetChanged();
    }

    public void loadData(List<RedHallListItemEntity> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RedHallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.redhall_item,parent,false);
        return new RedHallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RedHallViewHolder holder, int position) {
        holder.red_title.setText(items.get(position).getSentText());
        holder.group_name.setText(items.get(position).getNickname());
        Glide.with(context).load(items.get(position).getHeadImg()).transform(new CenterInside(), new GlideRoundTransform(context,30)).into(holder.group_head);
//        Glide.with(context).load(items.get(position).getHeadImg()).into(holder.group_head);
        if(items.get(position).getRewardState().equals("0")){
            holder.red_desc.setText("BoChat红包");
            if(items.get(position).getIsReceive().equals("0")){     //未领取
                holder.red_bg.setBackgroundResource(R.mipmap.money_frame);
                holder.money_icon.setImageResource(R.mipmap.money_icon);
            }else{
                holder.red_bg.setBackgroundResource(R.mipmap.money_frame_point);
                holder.money_icon.setImageResource(R.mipmap.money_point);
            }
        }else if(items.get(position).getRewardState().equals("1")){
            holder.red_desc.setText("已被领完");
            holder.red_bg.setBackgroundResource(R.mipmap.money_frame_point);
            holder.money_icon.setImageResource(R.mipmap.money_point);
        }else{
            holder.red_desc.setText("已失效");
            holder.red_bg.setBackgroundResource(R.mipmap.money_frame_point);
            holder.money_icon.setImageResource(R.mipmap.money_point);
        }
        if(items.get(position).getSendType().equals("1")){
            holder.red_type.setText("糖果红包");
        }else{
            holder.red_type.setText("零钱红包");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class RedHallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView group_head;
        TextView red_title;
        TextView red_desc;
        TextView red_type;
        ImageView money_icon;
        RelativeLayout red_bg;
        TextView group_name;

        public RedHallViewHolder(View itemView) {
            super(itemView);
            group_name=itemView.findViewById(R.id.group_name);
            group_head=itemView.findViewById(R.id.group_head);
            red_title=itemView.findViewById(R.id.red_title);
            red_desc=itemView.findViewById(R.id.red_desc);
            red_type=itemView.findViewById(R.id.red_type);
            money_icon=itemView.findViewById(R.id.money_icon);
            red_bg=itemView.findViewById(R.id.red_bg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onRedItemClickListener!=null){
                onRedItemClickListener.onRedClick(getPosition(),items.get(getPosition()));
            }
        }
    }

    public interface OnRedItemClickListener{
        void onRedClick(int position,RedHallListItemEntity entity);
    }

}
