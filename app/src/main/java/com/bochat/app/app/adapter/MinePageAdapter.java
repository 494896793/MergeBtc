package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

public class MinePageAdapter extends RecyclerView.Adapter<MinePageAdapter.ViewHolder> {

    public static final int ITEM_BALANCE_ID = 0x0011;
    public static final int ITEM_EXCHANGE_ID = 0x0012;
    public static final int ITEM_INVITATION_ID = 0x0013;
    public static final int ITEM_REALNAME_ID = 0x0014;
    public static final int ITEM_CARD_ID = 0x0015;
    public static final int ITEM_SETUP_ID = 0x0016;
    public static final int ITEM_PRIVILEGE = 0x0017;

    @IntDef({ITEM_BALANCE_ID, ITEM_EXCHANGE_ID, ITEM_INVITATION_ID, ITEM_REALNAME_ID, ITEM_CARD_ID, ITEM_SETUP_ID, ITEM_PRIVILEGE})
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemClickId {
    }

    private Context mContext;
    private List<Item> mData;

    private OnItemClickListener mListener;

    public MinePageAdapter(Context context, List<Item> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mine_main_list_item, null, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = mData.get(holder.getAdapterPosition());
                if (mListener != null)
                    mListener.onItemClick(item.id);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mData != null) {
            Item item = mData.get(position);
            holder.titleView.setText(item.title);
            holder.titleView.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(item.drawableId), null, null, null);
            if (item.tagDrawable != 0)
                holder.tagView.setImageResource(item.tagDrawable);
        }

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(@ItemClickId int id);
    }

    public static class Item {

        private String title;
        private int drawableId;
        private int tagDrawable;
        private int id;

        public Item(String title, int drawableId, int tagDrawable, @ItemClickId int id) {
            this.title = title;
            this.drawableId = drawableId;
            this.tagDrawable = tagDrawable;
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getDrawableId() {
            return drawableId;
        }

        public void setDrawableId(int drawableId) {
            this.drawableId = drawableId;
        }

        public int getTagDrawable() {
            return tagDrawable;
        }

        public void setTagDrawable(int tagDrawable) {
            this.tagDrawable = tagDrawable;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;
        public ImageView tagView;

        ViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.mine_main_item_title);
            tagView = itemView.findViewById(R.id.mine_main_tag);
        }
    }
}
