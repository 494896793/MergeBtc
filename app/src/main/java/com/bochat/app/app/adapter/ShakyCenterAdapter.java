package com.bochat.app.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.model.bean.ShakyEntity;
import com.bochat.app.model.util.TimeUtils;
import com.bochat.app.app.view.TopCircleImageView;
import com.bumptech.glide.Glide;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;

import java.util.List;

/**
 * 2019/6/12
 * Author LDL
 **/
public class ShakyCenterAdapter extends RecyclerView.Adapter<ShakyCenterAdapter.ShakyCenterViewHolder> {

    private Context mContext;
    private List<ShakyEntity> list;
    private OnItemClickListener onItemClickListener;

    public ShakyCenterAdapter(Context mContext,List<ShakyEntity> list){
        this.mContext=mContext;
        this.list=list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public ShakyCenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_shakycenter_item,null);
        return new ShakyCenterViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ShakyCenterViewHolder holder, int position) {
//        ViewOutlineProvider viewOutlineProvider=new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                outline.setRoundRect();
//            }
//        };
//        holder.rererere
        Glide.with(mContext).load(list.get(position).getImage()).into(holder.photo);
        holder.title_text.setText(list.get(position).getTitle());
        if(TimeUtils.getTimeExpendSecond(list.get(position).getTheshelves_time())>0){
            holder.statu_text.setVisibility(View.VISIBLE);
            holder.statu_text.setText("已结束");
        }else{
            holder.statu_text.setVisibility(View.INVISIBLE);
        }
    }

    public void refreshData(List<ShakyEntity> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void loadData(List<ShakyEntity> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ShakyCenterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TopCircleImageView photo;
        private TextView title_text;
        private TextView statu_text;
        private RelativeLayout rererere;

        public ShakyCenterViewHolder(View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.photo);
            title_text=itemView.findViewById(R.id.title_text);
            statu_text=itemView.findViewById(R.id.statu_text);
            rererere=itemView.findViewById(R.id.rererere);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener!=null){
                onItemClickListener.onItemClick(getPosition(),list.get(getPosition()));
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position,ShakyEntity shakyEntity);
    }

}
