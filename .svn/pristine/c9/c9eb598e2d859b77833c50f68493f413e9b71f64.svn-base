package com.bochat.app.app.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;

import java.util.ArrayList;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 13:42
 * Description :
 */

public class PlusMorePopupWindow extends BasePopupWindow {

    private OnPopupWindowItemClickListener mListener;

    public PlusMorePopupWindow(Context context, ArrayList<? extends Item> items) {
        super(context);
        setPopupGravity(Gravity.BOTTOM | Gravity.START);
        setBackground(0);

        RecyclerView recyclerView = findViewById(R.id.choose_pop);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ChooseAdapter adapter = new ChooseAdapter(context, items);
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

    }

    /**
     * PopupWindow Item Object
     */
    public static class Item {

        public CharSequence text;
        public int icon;

        public Item(String text, int icon) {
            setText(text);
            setIcon(icon);
        }

        public CharSequence getText() {
            return text;
        }

        public void setText(CharSequence text) {
            this.text = text;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.view_plus_more_dialog);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
        set.addAnimation(getDefaultAlphaAnimation());
        set.setDuration(200);
        return set;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
        set.addAnimation(getDefaultAlphaAnimation(false));
        set.setDuration(200);
        return set;
    }

    public void setOnPopupWindowItemClickListener(OnPopupWindowItemClickListener listener) {
        mListener = listener;
    }

    /**
     * PopupWindow Click Listener
     */
    public interface OnPopupWindowItemClickListener {
        void onPopupWindowItemClick(Item item, int position);
    }


    /**
     * PopupWindow Adapter View Holder
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.choose_item_icon);
            textView = itemView.findViewById(R.id.choose_item_text);
        }
    }

    /**
     * PopupWindow Adapter
     */
    public class ChooseAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        private Context mContext;
        private ArrayList<? extends Item> mList;

        public ChooseAdapter(Context context, ArrayList<? extends Item> list) {
            mContext = context;
            mList = list;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_dark, null);
            final ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = itemViewHolder.getAdapterPosition();
                    Item item = mList.get(position);
                    if (mListener != null)
                        mListener.onPopupWindowItemClick(item, position);
                    dismiss(false);
                }
            });
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

            Item item = mList.get(position);
            holder.imageView.setImageDrawable(mContext.getResources().getDrawable(item.icon));
            holder.textView.setText(item.text);

        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }
    }
}

