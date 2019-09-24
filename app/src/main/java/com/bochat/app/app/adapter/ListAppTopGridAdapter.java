package com.bochat.app.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.model.bean.DynamicShopTypeEntity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ListAppTopGridAdapter extends RecyclerView.Adapter<ListAppTopGridAdapter.ViewHolder> {

    private Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private List<DynamicShopTypeEntity> items = new ArrayList<>();

    public ListAppTopGridAdapter(Context context) {
        mContext = context;
    }

    public void notifyData(List<DynamicShopTypeEntity> tests) {
        items.addAll(tests);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_app_top_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final DynamicShopTypeEntity item = items.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(v, item);
            }
        });
//        holder.icon.setType(SpImageView.DEFAUT_ROUND_RADIUS);
        Glide.with(mContext).load(item.getIconImage()).into(holder.icon);
        holder.text.setText(item.getDictLabel());
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class TestItem {
        public int itemIcon;
        public String itemName;

        public TestItem() {

        }

        public TestItem(int icon, String name) {
            itemIcon = icon;
            itemName = name;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public SpImageView icon;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_top_grid_icon);
            text = itemView.findViewById(R.id.item_top_grid_text);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, DynamicShopTypeEntity entity);
    }
}
