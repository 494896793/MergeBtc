package com.bochat.app.app.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.FriendEntity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecommendFriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    private OnAddFriendListener mAddFriendListener;
    private OnClickChangedRecommendFriendListener mClickChangedListener;

    private List<FriendEntity> mData = new ArrayList<>();

    private Context mContext;
    private View mHeaderView;

    public RecommendFriendListAdapter(Context context) {
        mContext = context;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(List<FriendEntity> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new RecommendFriendHeaderViewHolder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_friend, parent, false);
        return new RecommendFriendListViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_HEADER) {
            RecommendFriendHeaderViewHolder headerViewHolder = ((RecommendFriendHeaderViewHolder) holder);
            ObjectAnimator animator = ObjectAnimator.ofFloat(headerViewHolder.recommendChangeRefresh, "rotation", 0, 720);
            animator.setDuration(1000);
            animator.start();
            headerViewHolder.recommendChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickChangedListener != null)
                        mClickChangedListener.onChanged();
                }
            });
            return;
        }
        ;


        final int pos = getRealPosition(holder);
        final FriendEntity friendEntity = mData.get(pos);
        RecommendFriendListViewHolder itemViewHolder = (RecommendFriendListViewHolder) holder;
        itemViewHolder.name.setText(friendEntity.getNickname());
        Glide.with(mContext).load(friendEntity.getHead_img()).into(itemViewHolder.icon);
        itemViewHolder.content.setText(friendEntity.getSignature());
        itemViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAddFriendListener != null)
                    mAddFriendListener.onAddFriend(friendEntity);
            }
        });

    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    public class RecommendFriendHeaderViewHolder extends RecyclerView.ViewHolder {

        View recommendChange;
        ImageView recommendChangeRefresh;

        public RecommendFriendHeaderViewHolder(View itemView) {
            super(itemView);
            recommendChange = itemView.findViewById(R.id.recommend_change_btn);
            recommendChangeRefresh = itemView.findViewById(R.id.recommend_change_refresh);
        }
    }

    public class RecommendFriendListViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView icon;
        TextView content;
        Button add;

        public RecommendFriendListViewHolder(View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.recommend_friend_add);
            name = itemView.findViewById(R.id.recommend_friend_name);
            icon = itemView.findViewById(R.id.recommend_friend_icon);
            content = itemView.findViewById(R.id.recommend_friend_signature);
        }
    }

    public interface OnAddFriendListener {
        void onAddFriend(FriendEntity entity);
    }

    public interface OnClickChangedRecommendFriendListener {
        void onChanged();
    }

    public void setOnAddFriendListener(OnAddFriendListener listener) {
        mAddFriendListener = listener;
    }

    public void setOnClickChangedRecommendFriendListener(OnClickChangedRecommendFriendListener listener) {
        mClickChangedListener = listener;
    }
}
