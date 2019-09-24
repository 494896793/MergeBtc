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
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;
import java.util.List;

import static com.bochat.app.model.constant.Constan.FRIEND_APPLY_ACCOUNT;
import static com.bochat.app.model.constant.Constan.FRIEND_APPLY_CODE;

/**
 * 2019/4/22
 * Author LDL
 **/
public class FriendApplyAdapter extends RecyclerView.Adapter<FriendApplyAdapter.FriendApplyViewHolder> {

    private  List<FriendApplyEntity> list;
    private Context context;
    private FriendApplyAdapter.OnItemClickListenner onItemClickListenner;
    private List<FriendEntity> friendList ;

    public FriendApplyAdapter(Context context, List<FriendApplyEntity> list){
        this.context=context;
        if(list==null){
            list=new ArrayList<>();
        }
        this.list=list;
        this.friendList = CachePool.getInstance().friend().getAll();
    }

    public void refreshOne(int position,FriendApplyEntity friendApplyEntity){
        list.remove(position);
        list.add(position,friendApplyEntity);
        notifyItemChanged(position);
    }

    public void refreshData(List<FriendApplyEntity> list){
        if(list==null){
            list=new ArrayList<>();
        }
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListenner(FriendApplyAdapter.OnItemClickListenner onItemClickListenner){
        this.onItemClickListenner=onItemClickListenner;
    }


    @NonNull
    @Override
    public FriendApplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_apply_item,null,false);
        return new FriendApplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendApplyViewHolder holder, int position) {
        if(friendList!=null){
            for(int i=0;i<friendList.size();i++){
                if(list.get(position).getProposer_id().equals(friendList.get(i).getId()+"") && friendList.get(i).getFriend_state() == 1){
                    list.get(position).setApply_state("2");
                    break;
                }
            }
        }
        if(list.get(position).getApply_state().equals("1")){
            holder.read_tx.setText("查看");
            holder.read_tx.setTextColor(context.getResources().getColor(R.color.color_4591EC));
            holder.read_tx.setBackground(context.getResources().getDrawable(R.drawable.apply_enable));
        }else if(list.get(position).getApply_state().equals("0")){
            holder.read_tx.setText("已拒绝");
            holder.read_tx.setTextColor(context.getResources().getColor(R.color.color_999999));
            holder.read_tx.setBackground(context.getResources().getDrawable(R.drawable.apply_unenable));
        }else if(list.get(position).getApply_state().equals("4")){
            holder.read_tx.setText("已过期");
            holder.read_tx.setTextColor(context.getResources().getColor(R.color.color_999999));
            holder.read_tx.setBackground(context.getResources().getDrawable(R.drawable.apply_unenable));
        }else{
            holder.read_tx.setTextColor(context.getResources().getColor(R.color.color_4591EC));
            holder.read_tx.setText("已通过");
            holder.read_tx.setBackground(context.getResources().getDrawable(R.drawable.apply_enable));
        }
        holder.content.setText(list.get(position).getApply_text());
        if(list.get(position).getSourceType()==FRIEND_APPLY_ACCOUNT){
            holder.infoFrom.setText("账号查找");
        }else if(list.get(position).getSourceType()==FRIEND_APPLY_CODE){
            holder.infoFrom.setText("二维码扫码");
        }else {
            holder.infoFrom.setText("群人员列表");
        }
        holder.userName.setText(list.get(position).getNickname());
        Glide.with(context).load(list.get(position).getHead_img()).error(R.mipmap.ic_default_head).transform(new CenterCrop(),new GlideRoundTransform(context,context.getResources().getDimensionPixelOffset(R.dimen.dp_10))).into(holder.head_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FriendApplyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView read_tx;
        TextView content;
        TextView userName;
        TextView infoFrom;
        ImageView head_img;


        public FriendApplyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            read_tx=itemView.findViewById(R.id.read_tx);
            content=itemView.findViewById(R.id.content);
            userName=itemView.findViewById(R.id.userName);
            infoFrom=itemView.findViewById(R.id.infoFrom);
            head_img=itemView.findViewById(R.id.head_img);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListenner!=null){
                onItemClickListenner.onItemClick(getPosition(),list.get(getPosition()));
            }
        }
    }

    public interface OnItemClickListenner{
        void onItemClick(int position, FriendApplyEntity friendApplyEntity);
    }

}
