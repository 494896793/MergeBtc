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
import com.bochat.app.model.bean.DynamicNoticeEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

/**
 * 2019/5/9
 * Author LDL
 **/
public class DynamicNoticeAdapter extends RecyclerView.Adapter<DynamicNoticeAdapter.DynamicNoticeViewHolder> {

    private List<DynamicNoticeEntity> list;
    private Context mContext;
    private OnChildItemClick onChildItemClick;

    public void setOnChildItemClick(OnChildItemClick onChildItemClick){
        this.onChildItemClick=onChildItemClick;
    }

    public DynamicNoticeAdapter(List<DynamicNoticeEntity> list,Context mContext){
        this.list=list;
        this.mContext=mContext;
    }

    public void refreshData(List<DynamicNoticeEntity> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void loadData(List<DynamicNoticeEntity> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DynamicNoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_dynamic_item,null);
        return new DynamicNoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicNoticeViewHolder holder, int position) {
       /* if(TextUtils.isEmpty(list.get(position).getContent())){
//            holder.content_text.setText("");
        }else {
//            holder.content_text.setText("\t\t" + Html.fromHtml(list.get(position).getContent()));
        }*/
        holder.group_text.setText(list.get(position).getSource());
        holder.date_text.setText(list.get(position).getReleaseTime());
        holder.title_text.setText(list.get(position).getTitle());
        if (list.get(position).getPicture() == null){
            Glide.with(mContext).load(R.mipmap.notice_img).error(R.mipmap.notice_img).into(holder.notice_img);
        }else {
            Glide.with(mContext).load(list.get(position).getPicture()).transform(new CenterCrop(), new GlideRoundTransform(mContext,2))
                    .error(R.mipmap.notice_img).into(holder.notice_img);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DynamicNoticeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title_text;
        TextView date_text;
        TextView group_text;
        ImageView notice_img;

        public DynamicNoticeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            content_text=itemView.findViewById(R.id.content_text);
            title_text=itemView.findViewById(R.id.title_text);
            date_text=itemView.findViewById(R.id.date_text);
            group_text=itemView.findViewById(R.id.group_text);
            notice_img=itemView.findViewById(R.id.notice_image);
        }

        @Override
        public void onClick(View v) {
            if(onChildItemClick!=null){
                onChildItemClick.onItemClick(getPosition(),list.get(getPosition()));
            }
        }
    }

    public interface OnChildItemClick{
        void onItemClick(int position,DynamicNoticeEntity entity);
    }

}
