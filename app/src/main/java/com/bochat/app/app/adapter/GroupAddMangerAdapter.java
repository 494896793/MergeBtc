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
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by guoying ${Date} and ${Month}
 */
public class GroupAddMangerAdapter extends RecyclerView.Adapter<GroupAddMangerAdapter.AddManagerViewHolder> {
    int[] checkImaga = {R.mipmap.administrators_choice,R.mipmap.administrators_choice_sel};
    private Context mContext;
    private List<GroupMemberEntity> mData;
    private onAddManagerItemListener listener;
    Map<Integer,Boolean> CheckMap;

    public GroupAddMangerAdapter(Context mContext, List<GroupMemberEntity> mList) {


        this.mContext = mContext;
        this.mData = mList;

        CheckMap = new HashMap<>();

        for (int i = 0;i<mList.size();i++){
            setItemClick(i,false);
        }

//        LogUtil.LogDebug("ggyy","CheckMap ="+CheckMap);

    }

    @NonNull
    @Override
    public AddManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cv_group_add_manager,parent,false);
        AddManagerViewHolder addManagerViewHolder = new AddManagerViewHolder(view);
        return addManagerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AddManagerViewHolder holder, final int position) {

        GroupMemberEntity entity = mData.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(entity.getHead_img())
                .transform(new CircleCrop())
                .error(R.mipmap.ic_default_head).into(holder.userImage);
        holder.userNickName.setText(entity.getNickname());
        holder.userId.setText("ID"+entity.getMember_id());
        holder.addManagerImageCheck.setVisibility(View.VISIBLE);
        if (!isItemCheck(position)){
          holder.addManagerImageCheck.setImageResource(R.mipmap.administrators_choice);
        }else {
            holder.addManagerImageCheck.setImageResource(R.mipmap.administrators_choice_sel);
        }

        // 判断点击选择更换图片
//        holder.addManagerImageCheck.setImageResource();
        holder.addManagerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemCheck(position)){
                    holder.addManagerImageCheck.setImageResource(R.mipmap.administrators_choice);
                    setItemClick(position,false);
                    listener.onReMoveManageItemOnclik(position);
                }else{
                    holder.addManagerImageCheck.setImageResource(R.mipmap.administrators_choice_sel);
                    setItemClick(position,true);
                    listener.onAddManagerItemOnclik(position,String.valueOf(mData.get(position).getMember_id()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int getItemPosition(String memberId){
        if(mData != null && !mData.isEmpty()){
            for(int i = 0; i < mData.size(); i++){
                if(Long.valueOf(memberId) == mData.get(i).getMember_id()){
                    return i;
                }
            }
        }
        return -1;
    }
    

    public void setItemClick(int position,boolean isCheck){
        CheckMap.put(position,isCheck);
    }

    public boolean isItemCheck(int position){
        return CheckMap.get(position);
    }

    public void add( List<GroupMemberEntity> list){
        for (int i = 0;i<list.size();i++){
            setItemClick(i,false);
        }
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }


    class AddManagerViewHolder extends RecyclerView.ViewHolder{
        ImageView userImage;
        TextView userNickName;
        TextView userId;
        ImageView addManagerImageCheck;
        RelativeLayout addManagerItem;
        public AddManagerViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.add_manager_user_img);
            userNickName = itemView.findViewById(R.id.add_manager_user_nick_name);
            userId = itemView.findViewById(R.id.add_manager_user_member);
            addManagerImageCheck = itemView.findViewById(R.id.add_manager_check);
            addManagerItem = itemView.findViewById(R.id.add_manager_item);

        }
    }

    public void setOnAddManagerItemListener(onAddManagerItemListener listener){
        this.listener = listener;
    }

    public interface onAddManagerItemListener{
        void onAddManagerItemOnclik(int positon,String memberId);
        void onReMoveManageItemOnclik(int position);
    }
}
