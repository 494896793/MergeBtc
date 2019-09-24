package com.bochat.app.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.view.CommandDailog;
import com.bochat.app.model.bean.BankCard;
import com.bochat.app.model.util.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

/**
 * Create by ggyy on 09
 */
public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.BankCardViewHolder> {
    private Context mContext;
    private List<BankCard> mData;
    private OnBankItemOnClickListener itemOnClickListener;

    public BankCardAdapter(Context mContxt, List<BankCard> mData) {
        this.mContext = mContxt;
        this.mData = mData;
    }

    @NonNull
    @Override
    public BankCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_bank_card,parent,false);
        BankCardViewHolder viewHolder = new BankCardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BankCardViewHolder holder, final int position) {
        final BankCard bankCard = mData.get(position);
        Glide.with(mContext).load(bankCard.getBankIcon()).into(holder.bankIcon);
        holder.bankName.setText(bankCard.getName());
//        holder.bankType.setText(bankCard.getCardType());
        holder.bankID.setText(bankCard.getBankNo());
        holder.bankItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClickListener.ItemOnClicke(bankCard);
            }
        });
        //设置背景颜色
        Glide.with(mContext).load(bankCard.getBankColor()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                holder.bankItem.setBackground(resource);

            }
        });
        holder.bankDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CommandDailog dailog=new CommandDailog(mContext, R.style.CommandDialog);


                dailog.setMessage("是否删除该银行卡？");
                dailog.setYesOnclickListener("确定", new CommandDailog.onYesOnclickListener() {
                    @Override
                    public void onYesOnclick() {
                        // todo接口回调
                        itemOnClickListener.ItemDelete(bankCard);
                        dailog.dismiss();

                    }
                });
                dailog.setNoOnclickListener("取消", new CommandDailog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dailog.dismiss();
                    }
                });
                dailog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class BankCardViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout bankItem;
        ImageView bankIcon;
        TextView bankName;
        TextView bankType;
        ImageView bankDelete;
        TextView bankID;
        public BankCardViewHolder(View itemView) {
            super(itemView);
            bankItem = itemView.findViewById(R.id.bank_item_background);
            bankName = itemView.findViewById(R.id.item_bank_card_name);
            bankIcon = itemView.findViewById(R.id.item_bank_imag);
            bankType = itemView.findViewById(R.id.item_bank_card_type);
            bankDelete = itemView.findViewById(R.id.item_bank_card_delete);
            bankID = itemView.findViewById(R.id.item_bank_card_id);
        }
    }

    public void removeData(int position) {
        mData.remove(position);
        notifyItemRemoved(position);

    }


  /*  private Drawable getDrawable(int position){
        Drawable mDrawable;
        int m = position % 3;
        if (m == 0){
            mDrawable = ContextCompat.getDrawable(mContext, R.mipmap.wallet_card_blue);
        }else if(m == 1){
            mDrawable = ContextCompat.getDrawable(mContext, R.mipmap.wallet_card_green);
        }else {

            mDrawable = ContextCompat.getDrawable(mContext, R.mipmap.wallet_card_red);
        }
        return mDrawable;
    }*/

    public void setItemOnClickListener(OnBankItemOnClickListener listener){
        this.itemOnClickListener = listener;

    }
    public interface OnBankItemOnClickListener {
        void ItemDelete(BankCard bankCard);
        void ItemOnClicke(BankCard bankCard);
    }
}




