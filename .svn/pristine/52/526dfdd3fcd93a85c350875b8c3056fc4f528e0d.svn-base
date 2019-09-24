package com.bochat.app.app.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterRechargeVip;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.PrivilegeEntity;
import com.bochat.app.model.bean.PrivilegeListEntity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PrivilegeAdapter extends RecyclerView.Adapter<PrivilegeAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<PrivilegeEntity> mData = new ArrayList<>();

    private OnItemClickListener mListener;

    public PrivilegeAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.privilege_layout_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final PrivilegeEntity entity = mData.get(position);

        Resources resources = mContext.getResources();

        //设置名称
        holder.mPrivilegeName.setText(entity.getPrivilegeName());
        //设置描述
//        holder.mPrivilegeDes.setText(entity.getPrivilegeDesc());

        //设置有效时间
        if (entity.equalDateType(PrivilegeEntity.PRIVILEGE_DATE_TYPE_FOREVER))
            holder.mPrivilegeTermOfValidity.setText(resources.getString(R.string.privilege_valid_forever, "永久有效"));
        else
            holder.mPrivilegeTermOfValidity.setText(resources.getString(R.string.privilege_valid_time, entity.getStartTime(), entity.getEndTime()));

        //加载背景图片
        Glide.with(mContext).load(entity.getBackgrooundImg()).into(holder.mPrivilegeBackground);

        holder.mPrivilegeRenewButton.setOnClickListener(this);
        holder.mPrivilegeRenewButton.setTextColor(resources.getColor(
                entity.equalEnableFlag(PrivilegeEntity.PRIVILEGE_FLAG_NORMAL) ?
                        R.color.privilege_used :
                        R.color.privilege_expire));
        holder.mPrivilegeRenewButton.setVisibility(
                entity.equalType(PrivilegeEntity.PRIVILEGE_TYPE_GENESIS_RESIDENTS) ?
                        View.GONE :
                        View.VISIBLE);

        holder.mPrivilegeState.setText(entity.equalEnableFlag(PrivilegeEntity.PRIVILEGE_FLAG_NORMAL) ? "使用中" : "已到期");
        holder.mPrivilegeState.setBackground(resources.getDrawable(
                entity.equalEnableFlag(PrivilegeEntity.PRIVILEGE_FLAG_NORMAL) ?
                        R.drawable.privilege_state_used_shape :
                        R.drawable.privilege_state_expire_shape));
        holder.mPrivilegeState.setVisibility(
                entity.equalType(PrivilegeEntity.PRIVILEGE_TYPE_GENESIS_RESIDENTS) ?
                        View.GONE :
                        View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onItemClick(entity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void onRefresh(PrivilegeListEntity entity) {
        mData.clear();
        mData.addAll(entity.getItems());
        notifyDataSetChanged();
    }

    public void onLoadMore(PrivilegeListEntity entity) {
        mData.addAll(entity.getItems());
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.privilege_btn) {
            ULog.d("去续费咯");
            Router.navigation(new RouterRechargeVip());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PrivilegeEntity entity);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 特权名称
         */
        private TextView mPrivilegeName;

        /**
         * 特权状态
         */
        private TextView mPrivilegeState;

        /**
         * 特权描述
         */
//        private TextView mPrivilegeDes;
        /**
         * 特权有效期
         */
        private TextView mPrivilegeTermOfValidity;

        /**
         * 续费按钮
         */
        private Button mPrivilegeRenewButton;

        /**
         * 背景
         */
        private ImageView mPrivilegeBackground;

        public ViewHolder(View itemView) {
            super(itemView);

            mPrivilegeName = itemView.findViewById(R.id.privilege_name);
            mPrivilegeState = itemView.findViewById(R.id.privilege_state);
//            mPrivilegeDes = itemView.findViewById(R.id.privilege_des);
            mPrivilegeTermOfValidity = itemView.findViewById(R.id.privilege_term_of_validity);
            mPrivilegeRenewButton = itemView.findViewById(R.id.privilege_btn);
            mPrivilegeBackground = itemView.findViewById(R.id.privilege_background);
        }
    }
}
