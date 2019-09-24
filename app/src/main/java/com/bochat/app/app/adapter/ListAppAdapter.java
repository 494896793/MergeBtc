package com.bochat.app.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.config.ResouceConfig;
import com.bochat.app.model.bean.DynamicShopGameEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * 2019/5/8
 * Author LDL
 **/
public class ListAppAdapter extends RecyclerView.Adapter<ListAppAdapter.ListAppViewHolder> {

    private List<DynamicShopGameEntity> items;
    private Context mContext;
    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ListAppAdapter(List<DynamicShopGameEntity> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ListAppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_view, null);
        return new ListAppViewHolder(view);
    }

    public void onRefresh(List<DynamicShopGameEntity> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void onLoad(List<DynamicShopGameEntity> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ListAppViewHolder holder, int position) {
        holder.declare_text.setText(items.get(position).getDescribes());
        holder.label_text.setText(items.get(position).getName());

        int radius = ResourceUtils.dip2px(mContext, R.dimen.dp_2);
        int iconSize = ResourceUtils.dip2px(mContext, R.dimen.dp_40);
        RoundedCorners roundedCorners = new RoundedCorners(radius);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(iconSize, iconSize);
        Glide.with(mContext).load(items.get(position).getImage()).apply(options).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ListAppViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView icon;
        TextView label_text;
        TextView declare_text;
        RelativeLayout cardView;

        public ListAppViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            icon = itemView.findViewById(R.id.icon);
            label_text = itemView.findViewById(R.id.label_text);
            declare_text = itemView.findViewById(R.id.declare_text);
            cardView = itemView.findViewById(R.id.card_view_container);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition(), items.get(getPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, DynamicShopGameEntity entity);
    }

}
