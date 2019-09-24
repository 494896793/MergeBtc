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
import com.bochat.app.model.bean.FriendEntity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 2019/4/19
 * Author LDL
 **/
public class AddressUserAdapter extends RecyclerView.Adapter<AddressUserAdapter.AddressUserViewHolder> {

    private List<FriendEntity> items;
    private Context context;
    private AddressUserAdapter.OnItemClickListenner onItemClickListenner;

    public void setOnItemClickListenner(AddressUserAdapter.OnItemClickListenner onItemClickListenner){
        this.onItemClickListenner=onItemClickListenner;
    }

    public AddressUserAdapter(Context context,List<FriendEntity> items){
        this.items=items;
        this.context=context;
    }

    public void refreshData(List<FriendEntity> items){
        this.items=items;
        notifyDataSetChanged();
    }

    public void loadData(List<FriendEntity> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= null;
        if(viewType==0){
            view=LayoutInflater.from(context).inflate(R.layout.pinyin_item,null,false);
        }else if(viewType == 1){
            view=LayoutInflater.from(context).inflate(R.layout.fragment_address_user_item,null,false);
        } else if(viewType == 2){
            view=LayoutInflater.from(context).inflate(R.layout.item_address_book_user_dark_total_num,null,false);
        } else {
            view=LayoutInflater.from(context).inflate(R.layout.item_address_book_user_dark_notification,null,false);
        }
        return new AddressUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressUserViewHolder holder, int position) {
        int type = getItemViewType(position);
        if(type == 0){
            holder.pinyin_tx.setText(items.get(position).getFirstPinYin());
        }else if(type == 1){
            Glide.with(context).load(items.get(position).getHead_img()).into(holder.head_img);
            holder.userName.setText(items.get(position).getNickname());
            holder.userSign.setText(items.get(position).getSignature());
        } else if(type == 2){
            holder.totalCount.setText("共"+items.get(position).getNickname()+"位好友");
        } else {
            int num = 0;
            try {
                num = Integer.valueOf(items.get(position).getNickname());
            } catch (Exception e){
            }
            if(num == 0){
                holder.notificationCount.setVisibility(View.INVISIBLE);
            } else {
                String count = num > 99 ? "99+" : String.valueOf(num);
                holder.notificationCount.setText(count);
                holder.notificationCount.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(items==null){
            return 0;
        }else{
            return items.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    class AddressUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SpImageView head_img;
        TextView userName;
        TextView userSign;
        TextView pinyin_tx;
        TextView totalCount;
        TextView notificationCount;
        
        public AddressUserViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            userSign=itemView.findViewById(R.id.userSign);
            userName=itemView.findViewById(R.id.userName);
            head_img=itemView.findViewById(R.id.head_img);
            pinyin_tx=itemView.findViewById(R.id.pinyin_tx);
            totalCount=itemView.findViewById(R.id.user_dark_total_num);
            notificationCount=itemView.findViewById(R.id.user_dark_notification_num);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListenner!=null){
                if(items.get(getPosition()).getType()==1){
                    onItemClickListenner.onItemClick(getPosition(), (FriendEntity) items.get(getPosition()));
                } else if(items.get(getPosition()).getType()==3){
                    onItemClickListenner.onNotificationClick();
                }
            }
        }
    }

    public interface OnItemClickListenner{
        void onItemClick(int position, FriendEntity friendEntity);
        void onNotificationClick();
    }
}
