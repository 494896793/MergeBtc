package com.bochat.app.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.activity.RedPacketDetailActivity;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.model.bean.RedHallDetailEntity;
import com.bochat.app.model.bean.RedPacketRecordEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;

/**
 * 2019/7/18
 * Author LDL
 **/
public class RedHallDetailAdapter extends RecyclerView.Adapter<RedHallDetailAdapter.RedHallDetailViewHolder> {

    private Context context;
    private List<RedPacketRecordEntity> list;
    private boolean isReceived=true;
    private RedHallDetailEntity redData;

    public RedHallDetailAdapter(Context context,RedHallDetailEntity redData,boolean isReceived){
        this.context=context;
        this.redData=redData;
        this.isReceived=isReceived;
        this.list=redData.getReceiveRecordList();
    }

    @NonNull
    @Override
    public RedHallDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        if(viewType==0){
            view = LayoutInflater.from(context).inflate(R.layout.reddetail_item_top,parent,false);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.activity_redpacketdetail_item,parent,false);
        }
        return new RedHallDetailAdapter.RedHallDetailViewHolder(view);
    }

    public void refreshData(List<RedPacketRecordEntity> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void loadData(List<RedPacketRecordEntity> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RedHallDetailViewHolder holder, int position) {
        if(getItemViewType(position)==0){
            if(redData!=null){
                Glide.with(context).load(redData.getHeadImg()).transform(new CenterInside(), new GlideRoundTransform(context,30)).into(holder.send_head_img);
                holder.title_text.setText(redData.getNickname());
                holder.notice_text.setText(redData.getSentText());
                if(isReceived){
                    if(redData.getSendType().equals("1")){      //糖果
                        holder.value_person.setText(redData.getReceiveMoney()+" "+redData.getCurrencyName());
                    }else{
                        holder.value_person.setText(redData.getReceiveMoney()+" 元");
                    }
                }else{
                    holder.value_person.setText("红包已被领完");
                }
                if(redData.getIsGroup().equals("0")){
                    holder.join_group_text.setText("加入群聊抢红包");
                }else{
                    holder.join_group_text.setText("进入群聊");
                }
                holder.receive_notice_text.setText("已领取"+redData.getReceiveNum()+"/"+redData.getSendNum()+"个,剩余"+redData.getSurplusNum()+"个");
                holder.enter_group.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(redData.getIsGroup().equals("0")){
                            Router.navigation(new RouterGroupDetail(redData.getGroupId()));
                        }else{
                            if( redData.getGroupDetails()!=null){
                                RongIM.getInstance().startGroupChat(context,
                                        redData.getGroupId(), redData.getGroupDetails().getGroupName());
                            }
                        }
                    }
                });
            }
        }else{
            holder.money_text.setText(list.get(position-1).getReceive_money()+"");
            holder.name_text.setText(list.get(position-1).getNickname());
            holder.time_text.setText(list.get(position-1).getReceive_time());
            if(TextUtils.isEmpty(list.get(position-1).getHeadImg())){
                list.get(position-1).setHeadImg("https://www.baidu.com/img/bd_logo1.png");
            }
            Glide.with(context).load(list.get(position-1).getHeadImg()).transform(new CenterInside(), new GlideRoundTransform(context,30)).into(holder.head_img);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        if(list==null||list.size()==0){
            return 1;
        }else{
            return list.size()+1;
        }
    }

    class RedHallDetailViewHolder extends RecyclerView.ViewHolder{

        TextView join_group_text;
        ImageView head_img;
        TextView name_text;
        TextView money_text;
        TextView time_text;
        ImageView send_head_img;
        TextView title_text;
        TextView notice_text;
        TextView value_person;
        TextView receive_notice_text;
        LinearLayout enter_group;

        public RedHallDetailViewHolder(View itemView) {
            super(itemView);
            join_group_text=itemView.findViewById(R.id.join_group_text);
            enter_group=itemView.findViewById(R.id.enter_group);
            head_img=itemView.findViewById(R.id.head_img);
            name_text=itemView.findViewById(R.id.name_text);
            money_text=itemView.findViewById(R.id.money_text);
            time_text=itemView.findViewById(R.id.time_text);
            send_head_img=itemView.findViewById(R.id.send_head_img);
            title_text=itemView.findViewById(R.id.title_text);
            notice_text=itemView.findViewById(R.id.notice_text);
            value_person=itemView.findViewById(R.id.value_person);
            receive_notice_text=itemView.findViewById(R.id.receive_notice_text);
        }
    }

}
