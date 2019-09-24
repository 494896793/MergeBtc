package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.activity.RedPacketDetailActivity;
import com.bochat.app.model.bean.RedPacketRecordEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;

import java.util.List;

/**
 * 2019/5/14
 * Author LDL
 **/
public class RedPacketDetailAdapter extends RecyclerView.Adapter<RedPacketDetailAdapter.RedPacketDetailViewHolder> {

    private List<RedPacketRecordEntity> list;
    private Context mContext;

    public RedPacketDetailAdapter(List<RedPacketRecordEntity> list,Context mContext){
        this.mContext=mContext;
        this.list=list;
    }

    @NonNull
    @Override
    public RedPacketDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_redpacketdetail_item,null);
        return new RedPacketDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RedPacketDetailViewHolder holder, int position) {
        holder.money_text.setText(list.get(position).getReceive_money()+"");
        holder.name_text.setText(list.get(position).getNickname());
        holder.time_text.setText(list.get(position).getReceive_time());
        if(TextUtils.isEmpty(list.get(position).getHeadImg())){
            list.get(position).setHeadImg("https://www.baidu.com/img/bd_logo1.png");
        }
        Glide.with(mContext).load(list.get(position).getHeadImg()).transform(new CenterInside(), new GlideRoundTransform(mContext,30)).into(holder.head_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RedPacketDetailViewHolder extends RecyclerView.ViewHolder{

        ImageView head_img;
        TextView name_text;
        TextView money_text;
        TextView time_text;

        public RedPacketDetailViewHolder(View itemView) {
            super(itemView);
            head_img=itemView.findViewById(R.id.head_img);
            name_text=itemView.findViewById(R.id.name_text);
            money_text=itemView.findViewById(R.id.money_text);
            time_text=itemView.findViewById(R.id.time_text);
        }
    }

}