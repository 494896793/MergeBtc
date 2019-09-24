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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

import io.rong.imlib.model.UserInfo;

/**
 * create by guoying ${Date} and ${Month}
 */
public class GroupMentioneAdapeter extends RecyclerView.Adapter<GroupMentioneAdapeter.MentionViewHolder> {
    private Context context;
    private List<UserInfo> list;

    private OnGroupMentioneItemListener listener;


    public GroupMentioneAdapeter(Context context, List<UserInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void add(List<UserInfo> userInfoList){
        list.clear();
        list.addAll(userInfoList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MentionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_mentione,parent,false);

        return new MentionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentionViewHolder holder, int position) {
        final UserInfo userInfo;
        userInfo = list.get(position);
        holder.userName.setText(userInfo.getName());
        holder.userId.setText("ID:"+userInfo.getUserId());
        Glide.with(context)
                .asBitmap()
                .load(userInfo.getPortraitUri())
                .transform(new CircleCrop())
                .error(R.mipmap.ic_default_head).into(holder.userIcon);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemOnclik(userInfo);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

     class MentionViewHolder extends RecyclerView.ViewHolder{
         ImageView userIcon;
         RelativeLayout layout;
         TextView userName;
         TextView userId;


        public MentionViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.select_mentione_nick_name);
            userId = itemView.findViewById(R.id.select_mentione_member_id);
            layout = itemView.findViewById(R.id.select_layout);
            userIcon = itemView.findViewById(R.id.select_mentione_icon);
        }
    }

    public void setOnGroupMentioneItemListener(OnGroupMentioneItemListener listener){
        this.listener = listener;
    }

    public interface OnGroupMentioneItemListener{
        void onItemOnclik(UserInfo userInfo);
    }
}
