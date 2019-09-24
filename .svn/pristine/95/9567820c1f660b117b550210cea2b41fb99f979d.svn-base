package com.bochat.app.app.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.util.DecimalFormatter;
import com.bochat.app.model.bean.TradingRulesEntity;
import com.bochat.app.model.modelImpl.MarketCenter.EntrustEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MarketQuotationBSAdapter extends RecyclerView.Adapter<MarketQuotationBSAdapter.MarketQuotationBSViewHolder> {
    // TODO 加入精度
    private Context mContext;
    private boolean isSell;

    private TradingRulesEntity mRules;
    private OnItemClickListener mListener;
    private List<EntrustEntity> mData = new ArrayList<>();

    public MarketQuotationBSAdapter(Context context, boolean sell) {
        mContext = context;
        isSell = sell;
        loadDefault();
    }

    public void setRules(TradingRulesEntity rules) {
        mRules = rules;
    }

    private void loadDefault() {
        for (int i = 0; i < 10; i++) {
            try {
                EntrustEntity entrustEntity = new EntrustEntity(new JSONObject("{}"));
                mData.add(entrustEntity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    @Override
    public MarketQuotationBSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.fragment_bs_socket_push_entrust_item, parent, false);
        return new MarketQuotationBSViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketQuotationBSViewHolder holder, final int position) {

        Resources res = mContext.getResources();
        EntrustEntity entrustEntity = mData.get(position);
        holder.mSortId.setText(String.valueOf(position + 1));
        holder.mPrice.setTextColor(isSell ? res.getColor(R.color.quotation_red_s) : res.getColor(R.color.quotation_green));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onItemClick(mData.get(position), position);
            }
        });
        if (entrustEntity != null) {

            if (mRules != null) {
                int currencyDecimalNum = mRules.getCurrencyDecimalNum();
                int decimalNum = mRules.getDecimalNum();

                double dPrice = entrustEntity.getPrice();
                double dTotalNum = entrustEntity.getTotalNum();

                BigDecimal price = DecimalFormatter.newInstance().scaleOfRules(dPrice, decimalNum, RoundingMode.HALF_EVEN);
                BigDecimal count = DecimalFormatter.newInstance().scaleOfRules(dTotalNum, currencyDecimalNum, RoundingMode.HALF_EVEN);

                holder.mPrice.setText(price.doubleValue() == 0 ? "" : price.toPlainString());
                holder.mCount.setText(count.doubleValue() == 0 ? "" : count.toPlainString());
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void isSell(boolean sell) {
        isSell = sell;
    }

    public void notifyData(List<EntrustEntity> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(EntrustEntity entity, int position);
    }

    class MarketQuotationBSViewHolder extends RecyclerView.ViewHolder {

        TextView mSortId;

        TextView mPrice;

        TextView mCount;

        public MarketQuotationBSViewHolder(View itemView) {
            super(itemView);

            mSortId = itemView.findViewById(R.id.sort_id);
            mPrice = itemView.findViewById(R.id.price);
            mCount = itemView.findViewById(R.id.count);
        }
    }

}
