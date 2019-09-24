package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.model.bean.EntrustApplyEntity;
import com.bochat.app.model.bean.EntrustApplyListEntity;

import java.util.ArrayList;
import java.util.List;

public class MarketQuotationEntrustAdapter extends RecyclerView.Adapter<MarketQuotationEntrustAdapter.MarketQuotationEntrustViewHolder> {

    private Context mContext;

    private List<EntrustApplyEntity> mData = new ArrayList<>();

    private OnEntrustItemClickListener mEntrustItemClickListener;

    public MarketQuotationEntrustAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public MarketQuotationEntrustViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.market_quotation_entrust_item, parent, false);
        return new MarketQuotationEntrustViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketQuotationEntrustViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEntrustItemClickListener != null)
                    mEntrustItemClickListener.onItemClick();
            }
        });

        final EntrustApplyEntity entity = mData.get(position);

        holder.mBuyCount.setText(entity.getNum());
        holder.mBuyPrice.setText(entity.getPrice());
        holder.mEntrustDate.setText(entity.getTrustTime());
        holder.mDealCount.setText(entity.getTurnoverNum());
        holder.mTransactionAmount.setText(entity.getTurnoverMoney());
        holder.mServiceCharge.setText(entity.getServiceCharge());

        holder.mEntrustTag.setText(entity.getType() == 0 ? "买入" : "卖出");
        holder.mBuyCountTopName.setText(entity.getType() == 0 ? "买入数量" : "卖出数量");
        holder.mBuyPriceTopName.setText(entity.getType() == 0 ? "买入单价" : "卖出单价");

        holder.mProgressBar.setBackground(entity.getType() == 0 ?
                mContext.getResources().getDrawable(R.drawable.entrust_progress_buy_shape) :
                mContext.getResources().getDrawable(R.drawable.entrust_progress_shape)
        );
        holder.mEntrustTag.setBackground(entity.getType() == 0 ?
                mContext.getResources().getDrawable(R.drawable.entrust_tag_buy_radius_shape) :
                mContext.getResources().getDrawable(R.drawable.entrust_tag_sell_radius_shape));

        double buyCount = Double.valueOf(TextUtils.isEmpty(holder.mBuyCount.getText().toString()) ? "0.0" : holder.mBuyCount.getText().toString());
        double dealCount = Double.valueOf(TextUtils.isEmpty(holder.mDealCount.getText().toString()) ? "0.0" : holder.mDealCount.getText().toString());

        switch (entity.getState()) {
            case 1:
                holder.mProgressState.setText("已完成");
                holder.mRevokeButton.setVisibility(View.GONE);
                holder.mProgressBar.setVisibility(View.GONE);
                break;
            case 2:
                double percent = (dealCount / buyCount) * 100;
                holder.mProgressState.setText(mContext.getResources().getString(R.string.market_quotation_percent_fmt, percent));
                holder.mProgressBar.setProgress((int) percent);
                break;
            case 3:
                holder.mProgressState.setText("已撤销");
                holder.mRevokeButton.setVisibility(View.GONE);
                holder.mProgressBar.setVisibility(View.GONE);
                break;
        }

        holder.mRevokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEntrustItemClickListener != null)
                    mEntrustItemClickListener.onRevoke(entity.getId());
            }
        });

    }

    private String getState(int state) {
        switch (state) {
            case 1:
                return "已完成";
            case 2:
                return "委托中";
            case 3:
                return "已撤销";
            default:
                return "";
        }

    }

    public void setOnEntrustItemClickListener(OnEntrustItemClickListener mEntrustItemClickListener) {
        this.mEntrustItemClickListener = mEntrustItemClickListener;
    }

    public void onRefresh(EntrustApplyListEntity entity) {
        mData.clear();
        mData.addAll(entity.getItems());
        notifyDataSetChanged();
    }

    public void onLoadMore(EntrustApplyListEntity entity) {
        mData.addAll(entity.getItems());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class MarketQuotationEntrustViewHolder extends RecyclerView.ViewHolder {

        /**
         * 买入数量
         */
        private TextView mBuyCount;

        private TextView mBuyCountTopName;
        /**
         * 买入单价
         */
        private TextView mBuyPrice;
        private TextView mBuyPriceTopName;
        /**
         * 委托时间
         */
        private TextView mEntrustDate;
        /**
         * 成交数量
         */
        private TextView mDealCount;
        /**
         * 成交金额
         */
        private TextView mTransactionAmount;
        /**
         * 手续费
         */
        private TextView mServiceCharge;
        /**
         * 标记买入或卖出
         */
        private TextView mEntrustTag;
        /**
         * 进度状态
         */
        private TextView mProgressState;
        /**
         * 进度条
         */
        private ProgressBar mProgressBar;
        /**
         * 撤销按钮
         */
        private TextView mRevokeButton;
        /**
         *
         */
        private TextView mEntrustBSRevoke;

        MarketQuotationEntrustViewHolder(View itemView) {
            super(itemView);

            mBuyCount = itemView.findViewById(R.id.buy_count_cell_des);
            mBuyCountTopName = itemView.findViewById(R.id.buy_count_top_name);
            mBuyPrice = itemView.findViewById(R.id.buy_price_cell_des);
            mBuyPriceTopName = itemView.findViewById(R.id.buy_price_top_name);
            mEntrustDate = itemView.findViewById(R.id.entrust_time_cell_des);
            mDealCount = itemView.findViewById(R.id.deal_count_cell_des);
            mTransactionAmount = itemView.findViewById(R.id.transaction_amount_cell_des);
            mServiceCharge = itemView.findViewById(R.id.service_charge_cell_des);
            mEntrustTag = itemView.findViewById(R.id.entrust_bs_txt);
            mProgressState = itemView.findViewById(R.id.entrust_bs_progress_state);
            mProgressBar = itemView.findViewById(R.id.entrust_bs_progress);
            mRevokeButton = itemView.findViewById(R.id.entrust_bs_revoke);
        }
    }

    public interface OnEntrustItemClickListener {
        void onItemClick();

        void onRevoke(int id);
    }
}