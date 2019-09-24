package com.bochat.app.app.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.fragment.dynamic.MarketQuotationOptionalFragment;
import com.bochat.app.common.util.NumberUtil;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity;

import java.util.ArrayList;
import java.util.List;

public class MarketQuotationOptionalAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    private OnMarketQuotationHeaderClickListener mMarketQuotationHeaderClickListener;
    private OnMarketQuotationItemClickListener mMarketQuotationItemClickListener;

    private Context mContext;
    private List<TransactionEntity> mData = new ArrayList<>();

    private View mHeaderView;


    public MarketQuotationOptionalAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.market_quotation_optional_header_view, parent, false);
            return new DynamicOptionsHeaderViewHolder(mHeaderView);
        }
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.market_quotation_optional_item, parent, false);
        return new DynamicOptionsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            final DynamicOptionsHeaderViewHolder headerViewHolder = (DynamicOptionsHeaderViewHolder) holder;

            headerViewHolder.mClickDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        headerViewHolder.mClickSortPrice.setChecked(false);
                        headerViewHolder.mClickSortUpAndDown.setChecked(false);
                    }
                    if (mMarketQuotationHeaderClickListener != null)
                        mMarketQuotationHeaderClickListener.onClickSort();
                }
            });
            headerViewHolder.mClickSortPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        headerViewHolder.mClickDefault.setChecked(false);
                        headerViewHolder.mClickSortUpAndDown.setChecked(false);
                    }
                    if (mMarketQuotationHeaderClickListener != null)
                        mMarketQuotationHeaderClickListener.onClickNewPrice(isChecked);
                }
            });
            headerViewHolder.mClickSortUpAndDown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        headerViewHolder.mClickSortPrice.setChecked(false);
                        headerViewHolder.mClickDefault.setChecked(false);
                    }
                    if (mMarketQuotationHeaderClickListener != null)
                        mMarketQuotationHeaderClickListener.onClickUpAndDown(isChecked);
                }
            });

            return;
        }

        final int pos = getRealPosition(holder);
        final TransactionEntity transactionEntity = mData.get(pos);
        DynamicOptionsViewHolder itemViewHolder = (DynamicOptionsViewHolder) holder;

        String buyerName = transactionEntity.getSellerName();
        String sellerName = " / " + transactionEntity.getBuyerName();

        SpannableString spannableBuyerName = new SpannableString(buyerName);
        spannableBuyerName.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_222222)), 0, spannableBuyerName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableBuyerName.setSpan(new AbsoluteSizeSpan(16, true), 0, spannableBuyerName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableBuyerName.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableBuyerName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString spannableSellerName = new SpannableString(sellerName);
        spannableSellerName.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_999999)), 0, spannableSellerName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableSellerName.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableSellerName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableSellerName.setSpan(new StyleSpan(Typeface.NORMAL), 0, spannableSellerName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(spannableBuyerName);
        spanBuilder.append(spannableSellerName);

        String RMB = transactionEntity.getRMB();
        String upToLow = transactionEntity.getUpToLow();
        String currentPrice = transactionEntity.getCurrentPrice();

        itemViewHolder.mDynamicMKItemName.setText(spanBuilder);
        itemViewHolder.mDynamicMKItemCount.setText("量 " + String.format("%.2f", NumberUtil.parseDouble(transactionEntity.getTotalNum())));


        itemViewHolder.mDynamicMKItemNewPrice.setText(!TextUtils.isEmpty(currentPrice) ? currentPrice : "0.00000000");
        itemViewHolder.mDynamicMKItemExChange.setText(!TextUtils.isEmpty(RMB) ? transactionEntity.getRMB() : "≈￥0.0000");

        itemViewHolder.mDynamicMKItemStockIndex.setBackgroundResource((!TextUtils.isEmpty(upToLow) && !upToLow.startsWith("-")) ? R.drawable.shape_down_green : R.drawable.shape_up_red);
        itemViewHolder.mDynamicMKItemStockIndex.setText(!TextUtils.isEmpty(upToLow) ? transactionEntity.getUpToLow() : "0.00%");

    }

    private void setClick(DynamicOptionsHeaderViewHolder holder, Drawable priceDrawable, Drawable upAndDownDrawable, int defaultColor, int priceColor, int upAndDownColor) {
        holder.mClickDefault.setTextColor(mContext.getResources().getColor(defaultColor));
        holder.mClickSortPrice.setTextColor(mContext.getResources().getColor(priceColor));
        holder.mClickSortUpAndDown.setTextColor(mContext.getResources().getColor(upAndDownColor));
        holder.mClickSortPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, priceDrawable, null);
        holder.mClickSortUpAndDown.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, upAndDownDrawable, null);
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    public void notifyData(List<TransactionEntity> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();

    }

    public void notifyClearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mHeaderView != null ? mData.size() + 1 : mData.size();
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

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }


    public void setMarketQuotationHeaderClickListener(OnMarketQuotationHeaderClickListener listenter) {
        mMarketQuotationHeaderClickListener = listenter;
    }

    public void setOnMarketQuotationItemClickListener(OnMarketQuotationItemClickListener listener) {
        mMarketQuotationItemClickListener = listener;
    }


    public class DynamicOptionsHeaderViewHolder extends RecyclerView.ViewHolder {

        CheckBox mClickDefault;
        CheckBox mClickSortPrice;
        CheckBox mClickSortUpAndDown;

        public DynamicOptionsHeaderViewHolder(View itemView) {
            super(itemView);
            mClickDefault = itemView.findViewById(R.id.default_sort_radio);
            mClickSortPrice = itemView.findViewById(R.id.price_sort_radio);
            mClickSortUpAndDown = itemView.findViewById(R.id.gain_sort_radio);
        }

    }

    public class DynamicOptionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout mDynamicMKItem;
        TextView mDynamicMKItemName;
        TextView mDynamicMKItemCount;
        TextView mDynamicMKItemNewPrice;
        TextView mDynamicMKItemExChange;
        Button mDynamicMKItemStockIndex;

        DynamicOptionsViewHolder(View itemView) {
            super(itemView);
            mDynamicMKItem = itemView.findViewById(R.id.dynamic_mk_item);
            mDynamicMKItemName = itemView.findViewById(R.id.dynamic_mk_item_name);
            mDynamicMKItemCount = itemView.findViewById(R.id.dynamic_mk_item_count);
            mDynamicMKItemNewPrice = itemView.findViewById(R.id.dynamic_mk_item_new_price);
            mDynamicMKItemExChange = itemView.findViewById(R.id.dynamic_mk_item_exchange);
            mDynamicMKItemStockIndex = itemView.findViewById(R.id.dynamic_mk_item_stock_index);
            mDynamicMKItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mMarketQuotationItemClickListener != null) {
                mMarketQuotationItemClickListener.onItemClick(v, mData.get(getRealPosition(this)));
            }
        }
    }

    public interface OnMarketQuotationItemClickListener {
        void onItemClick(View view, TransactionEntity entity);
    }

    public interface OnMarketQuotationHeaderClickListener {
        void onClickSort();

        void onClickNewPrice(boolean isAsc);

        void onClickUpAndDown(boolean isAsc);
    }
}
