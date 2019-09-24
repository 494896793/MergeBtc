package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.model.bean.GroupEntity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecommendGroupListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    private RecommendGroupListAdapter.OnAddGroupListener mAddGroupListener;
    private RecommendGroupListAdapter.OnClickChangedRecommendGroupListener mClickChangedListener;

    private List<GroupEntity> mData = new ArrayList<>();

    private Context mContext;
    private View mHeaderView;

    public RecommendGroupListAdapter(Context context) {
        mContext = context;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(List<GroupEntity> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new RecommendGroupListAdapter.RecommendGroupHeaderViewHolder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_group, parent, false);
        return new RecommendGroupListAdapter.RecommendGroupListViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }

        final int pos = getRealPosition(holder);
        final GroupEntity groupEntity = mData.get(pos);
        RecommendGroupListAdapter.RecommendGroupListViewHolder itemViewHolder = (RecommendGroupListAdapter.RecommendGroupListViewHolder) holder;
        itemViewHolder.name.setText(groupEntity.getGroup_name());
        Glide.with(mContext).load(groupEntity.getGroup_head()).into(itemViewHolder.icon);
        itemViewHolder.content.setText(groupEntity.getGroup_introduce());
        itemViewHolder.people.setText(mContext.getResources().getString(R.string.conversation_group_people_visitor, groupEntity.getPeople()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAddGroupListener != null)
                    mAddGroupListener.onAddGroup(groupEntity);
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

    public class RecommendGroupHeaderViewHolder extends RecyclerView.ViewHolder {

        View recommendChange;

        public RecommendGroupHeaderViewHolder(View itemView) {
            super(itemView);
            recommendChange = itemView.findViewById(R.id.recommend_change_btn);
        }
    }

    public class RecommendGroupListViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView icon;
        TextView content;
        TextView people;

        public RecommendGroupListViewHolder(View itemView) {
            super(itemView);
            people = itemView.findViewById(R.id.recommend_group_people);
            name = itemView.findViewById(R.id.recommend_group_name);
            icon = itemView.findViewById(R.id.recommend_group_icon);
            content = itemView.findViewById(R.id.recommend_group_signature);
        }
    }

    public interface OnAddGroupListener {
        void onAddGroup(GroupEntity entity);
    }

    public interface OnClickChangedRecommendGroupListener {
        void onChanged();
    }

    public void setOnAddGroupListener(RecommendGroupListAdapter.OnAddGroupListener listener) {
        mAddGroupListener = listener;
    }

    public void setOnClickChangedRecommendGroupListener(RecommendGroupListAdapter.OnClickChangedRecommendGroupListener listener) {
        mClickChangedListener = listener;
    }
}