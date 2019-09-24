package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.model.bean.GroupEntity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 2019/4/19
 * Author LDL
 **/
public class AddressGroupAdapter extends RecyclerView.Adapter<AddressGroupAdapter.AddressGroupViewHolder> {

    private List<PinYinGroupEntity> items;
    private Context context;
    private OnItemClickListener onItemClickListenner;

    public static class PinYinGroupEntity {
        private GroupEntity groupEntity;
        private int type;
        private String firstPinYin;
        public PinYinGroupEntity(){
        }
        public PinYinGroupEntity(GroupEntity groupEntity){
            this.groupEntity = groupEntity;
        }
        public GroupEntity getGroupEntity() {
            return groupEntity;
        }
        public void setGroupEntity(GroupEntity groupEntity) {
            this.groupEntity = groupEntity;
        }
        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }
        public String getFirstPinYin() {
            return firstPinYin;
        }
        public void setFirstPinYin(String firstPinYin) {
            this.firstPinYin = firstPinYin;
        }
    }
    
    public void setOnItemClickListenner(OnItemClickListener onItemClickListenner){
        this.onItemClickListenner=onItemClickListenner;
    }

    public AddressGroupAdapter(Context context, List<PinYinGroupEntity> items){
        this.items=items;
        this.context=context;
    }

    public void refreshData(List<PinYinGroupEntity> items){
        this.items=items;
        notifyDataSetChanged();
    }

    public void loadData(List<PinYinGroupEntity> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @NonNull
    @Override
    public AddressGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= null;
        if(viewType==0){
            view=LayoutInflater.from(context).inflate(R.layout.pinyin_item,null,false);
        }else if(viewType == 1){
            view=LayoutInflater.from(context).inflate(R.layout.fragment_address_user_item,null,false);
        } else {
            view=LayoutInflater.from(context).inflate(R.layout.item_address_book_user_dark_total_num,null,false);
        }
        return new AddressGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressGroupViewHolder holder, int position) {
        int type = getItemViewType(position);
        if(type == 0){
            holder.pinyin_tx.setText(items.get(position).getFirstPinYin());
        }else if(type == 1){
            Glide.with(context).load(items.get(position).getGroupEntity().getGroup_head()).into(holder.head_img);
            holder.userName.setText(items.get(position).getGroupEntity().getGroup_name());
            holder.userSign.setText("ID: "+ items.get(position).getGroupEntity().getGroup_id());
        } else {
            holder.totalCount.setText("共"+items.get(position).getGroupEntity().getGroup_name()+"个群聊");
        }
    }

    @Override
    public int getItemCount() {
        if(items == null){
            return 0;
        }else{
            return items.size();
        }
    }

    class AddressGroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        SpImageView head_img;
        TextView userName;
        TextView userSign;
        TextView pinyin_tx;
        TextView totalCount;

        public AddressGroupViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            userSign=itemView.findViewById(R.id.userSign);
            userName=itemView.findViewById(R.id.userName);
            head_img=itemView.findViewById(R.id.head_img);
            pinyin_tx=itemView.findViewById(R.id.pinyin_tx);
            totalCount=itemView.findViewById(R.id.user_dark_total_num);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListenner!=null){
                onItemClickListenner.onItemClick(getPosition(),items.get(getPosition()).getGroupEntity());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position,GroupEntity groupEntity);
    }

}
