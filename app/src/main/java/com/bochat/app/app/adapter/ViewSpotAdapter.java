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
import com.bochat.app.app.view.HorizontalListView;
import com.bochat.app.model.bean.ViewSpotItemEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;
import java.util.List;

/**
 * 2019/5/23
 * Author LDL
 **/
public class ViewSpotAdapter extends RecyclerView.Adapter<ViewSpotAdapter.ViewSpotViewHolder> {

    private List<ViewSpotItemEntity> list;
    private Context mContext;
    private ViewSpotGrideViewAdapter grideViewAdapter;
    private OnItemClickListenner onItemClickListenner;

    public void setOnItemClickListenner(OnItemClickListenner onItemClickListenner){
        this.onItemClickListenner=onItemClickListenner;
    }

    public ViewSpotAdapter(List<ViewSpotItemEntity> list,Context mContext){
        this.mContext=mContext;
        this.list=list;
    }

    public void refreshData(List<ViewSpotItemEntity> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void loadMore(List<ViewSpotItemEntity> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewSpotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;         //1-小缩略图展示；2-大缩略图展示；3-多缩略图展示
        if(viewType==1){
            view= LayoutInflater.from(mContext).inflate(R.layout.viewspot_small_photo_item,null);
        }else if(viewType==2){
            view= LayoutInflater.from(mContext).inflate(R.layout.viewspot_big_photo_item,null);
        }else{
            view= LayoutInflater.from(mContext).inflate(R.layout.viewspot_many_photo,null);
        }
        return new ViewSpotViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSpotViewHolder holder, int position) {
        holder.from_text.setText(list.get(position).getComeForm());
        holder.time_text.setText(list.get(position).getUpTime());
        if(position<3){
            holder.hot_point.setVisibility(View.VISIBLE);
        }else{
            holder.hot_point.setVisibility(View.GONE);
        }
        if(getItemViewType(position)==1){
            Glide.with(mContext).load(list.get(position).getShowImage()).transform(new CenterCrop(),new GlideRoundTransform(mContext)).into(holder.img);
            if(position<3){
                holder.title_text.setText("\t\t\t\t"+list.get(position).getTitle());
            }else{
                holder.title_text.setText(list.get(position).getTitle());
            }
        }else if(getItemViewType(position)==2){
            Glide.with(mContext).load(list.get(position).getShowImage()).transform(new CenterCrop(),new GlideRoundTransform(mContext)).into(holder.img);
            holder.title_text.setText(list.get(position).getTitle());
        }else{
            holder.title_text.setText(list.get(position).getTitle());
            if(!TextUtils.isEmpty(list.get(position).getShowImage())) {
                if (grideViewAdapter == null) {
                    List<String> imgs = formatImgs(list.get(position).getShowImage());
                    grideViewAdapter = new ViewSpotGrideViewAdapter(imgs, mContext);
                    holder.listview.setAdapter(grideViewAdapter);
                }else{
                    List<String> imgs = formatImgs(list.get(position).getShowImage());
                    grideViewAdapter.refresh(imgs);
                }
            }
        }
    }

    public List<String> formatImgs(String urlStr){
        List<String> imgList=new ArrayList<>();
        String[] imgs=urlStr.split(",");
        for(int i=0;i<imgs.length;i++){
            imgList.add(imgs[i]);
        }
        return imgList;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewSpotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView title_text;
        TextView time_text;
        TextView from_text;
        HorizontalListView listview;
        TextView hot_point;

        public ViewSpotViewHolder(View itemView) {
            super(itemView);
            hot_point=itemView.findViewById(R.id.hot_point);
            title_text=itemView.findViewById(R.id.title_text);
            img=itemView.findViewById(R.id.img);
            from_text=itemView.findViewById(R.id.from_text);
            time_text=itemView.findViewById(R.id.time_text);
            listview=itemView.findViewById(R.id.listview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListenner!=null){
                onItemClickListenner.Onclick(getPosition(),list.get(getPosition()));
            }
        }
    }

    public interface OnItemClickListenner{
        void Onclick(int position,ViewSpotItemEntity entity);
    }

}
