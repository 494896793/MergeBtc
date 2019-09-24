package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.model.bean.GroupForbiddenItemEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public class GroupManageForbiddenAdapter extends RecyclerView.Adapter<GroupManageForbiddenAdapter.ForbiddenViewHodler> {
    private Context mContext;
    private List<GroupForbiddenItemEntity> data;
    private OnForbiddenAdapterListener mListener;

    public GroupManageForbiddenAdapter(Context mContext, List<GroupForbiddenItemEntity> data) {
        this.mContext = mContext;
        this.data = data;
    }



    @NonNull
    @Override
    public ForbiddenViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_group_forbidden,parent,false);
        ForbiddenViewHodler forbiddenViewHodler = new ForbiddenViewHodler(view);

        return forbiddenViewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ForbiddenViewHodler holder, final int position) {
        GroupForbiddenItemEntity entity = data.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(entity.getHead_img())
                .transform(new CircleCrop())
                .error(R.mipmap.ic_default_head).into(holder.forbiddenHeader);
        holder.forbiddenName.setText(entity.getNickname());
        holder.forbiddenId.setText(String.valueOf(entity.getId()));
        holder.relieveForbidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 解除禁言
                mListener.onRelieveForbidden(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeData(int position) {
        data.remove(position);
        notifyDataSetChanged();


    }

    public void add(List<GroupForbiddenItemEntity> addMessageList) {
        //加载更多数据
        int position = data.size();
        data.addAll(position, addMessageList);
        notifyItemInserted(position);
    }

    public void refresh(List<GroupForbiddenItemEntity> newList) {
        //刷新数据
        data.clear();
        data.removeAll(data);
        data.addAll(newList);
        notifyDataSetChanged();
    }

    class ForbiddenViewHodler extends RecyclerView.ViewHolder{
        ImageView forbiddenHeader;
        TextView forbiddenName;
        TextView forbiddenId;
        Button relieveForbidden;

        public ForbiddenViewHodler(View itemView) {
            super(itemView);
            forbiddenHeader = itemView.findViewById(R.id.group_forbidden_img);
            forbiddenName = itemView.findViewById(R.id.group_forbidden_name);
            forbiddenId = itemView.findViewById(R.id.group_forbidden_id);
            relieveForbidden = itemView.findViewById(R.id.group_forbidden_btn);
        }
    }

    public void setOnForbididdenAdapterListener(OnForbiddenAdapterListener listener){
        this.mListener = listener;
    }

    public interface OnForbiddenAdapterListener{
        public void onRelieveForbidden(int position);
    }


}