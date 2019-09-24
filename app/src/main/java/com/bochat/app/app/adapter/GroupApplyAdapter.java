package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.model.bean.GroupApplyServerEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;
import java.util.List;

/**
 * 2019/4/22
 * Author LDL
 **/
public class GroupApplyAdapter extends RecyclerView.Adapter<GroupApplyAdapter.GroupApplyViewHolder> {

    private List<GroupApplyServerEntity> list;
    private Context context;
    private GroupApplyAdapter.OnItemClickListenner onItemClickListenner;

    public GroupApplyAdapter(Context context, List<GroupApplyServerEntity> list) {
        this.context = context;
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
    }

    public void refreshData(List<GroupApplyServerEntity> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListenner(GroupApplyAdapter.OnItemClickListenner onItemClickListenner) {
        this.onItemClickListenner = onItemClickListenner;
    }


    @NonNull
    @Override
    public GroupApplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_apply_item, null, false);
        return new GroupApplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupApplyViewHolder holder, int position) {

        if (list.get(position).getApplyState() == 0) {
            holder.read_tx.setText("查看");
            holder.read_tx.setTextColor(context.getResources().getColor(R.color.color_4591EC));
            holder.read_tx.setBackground(context.getResources().getDrawable(R.drawable.apply_enable));
        } else if (list.get(position).getApplyState() == 2) {
            holder.read_tx.setText("已拒绝");
            holder.read_tx.setTextColor(context.getResources().getColor(R.color.color_999999));
            holder.read_tx.setBackground(context.getResources().getDrawable(R.drawable.apply_unenable));
        } else if (list.get(position).getApplyState() == 3) {
            holder.read_tx.setText("已过期");
            holder.read_tx.setTextColor(context.getResources().getColor(R.color.color_999999));
            holder.read_tx.setBackground(context.getResources().getDrawable(R.drawable.apply_unenable));
        } else {
            holder.read_tx.setTextColor(context.getResources().getColor(R.color.color_4591EC));
            holder.read_tx.setText("已通过");
            holder.read_tx.setBackground(context.getResources().getDrawable(R.drawable.apply_enable));
        }
        GroupApplyServerEntity groupApplyServerEntity = list.get(position);
        holder.content.setText("申请加入" + groupApplyServerEntity.getGroupName());
        holder.infoFrom.setText("账号查找");
        holder.userName.setText(groupApplyServerEntity.getNickname());
        Glide.with(context).load(groupApplyServerEntity.getHeadImg()).error(R.mipmap.ic_default_head).transform(new CenterCrop(),new GlideRoundTransform(context,context.getResources().getDimensionPixelOffset(R.dimen.dp_10))).into(holder.head_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GroupApplyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView read_tx;
        TextView content;
        TextView userName;
        TextView infoFrom;
        ImageView head_img;


        public GroupApplyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            read_tx = itemView.findViewById(R.id.read_tx);
            content = itemView.findViewById(R.id.content);
            userName = itemView.findViewById(R.id.userName);
            infoFrom = itemView.findViewById(R.id.infoFrom);
            head_img = itemView.findViewById(R.id.head_img);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListenner != null) {
                onItemClickListenner.onItemClick(getPosition(), list.get(getPosition()));
            }
        }
    }

    public interface OnItemClickListenner {
        void onItemClick(int position, GroupApplyServerEntity friendApplyEntity);
    }

}
