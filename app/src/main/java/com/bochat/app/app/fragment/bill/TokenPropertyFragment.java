package com.bochat.app.app.fragment.bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.common.contract.bill.TokenPropertyContract;
import com.bochat.app.model.bean.UserCurrencyDataEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.mvp.view.BaseFragment;
import com.bumptech.glide.Glide;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26 0026 17:17
 * Description : Token资产
 */
public class TokenPropertyFragment extends BaseFragment<TokenPropertyContract.Presenter> implements TokenPropertyContract.View {

    @Inject
    TokenPropertyContract.Presenter presenter;

    @BindView(R.id.mine_wallet_token_property_money)
    TextView totalMoney;
    @BindView(R.id.mine_wallet_token_list)
    ListView tokenList;
    
    private ArrayList<UserCurrencyEntity> test = new ArrayList<>();
    
    private CommonAdapter<UserCurrencyEntity> adapter;
    
    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected TokenPropertyContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_token_property_gy, container, false);
        return view;
    }

    @Override
    protected void initWidget() {

        adapter = new CommonAdapter<UserCurrencyEntity>(getViewContext(), R.layout.item_token_property, test) {
            @Override
            protected void convert(ViewHolder viewHolder, UserCurrencyEntity item, int position) {
                viewHolder.setText(R.id.item_token_property_name, item.getbName());
                ImageView view = viewHolder.getView(R.id.item_token_property_icon);
                Glide.with(getActivity()).load(item.getbIamge()).into(view);
                viewHolder.setText(R.id.item_token_property_count, item.getTotalAmount());
                viewHolder.setText(R.id.item_token_property_to_rmb, "≈ ¥ " + item.getCnyPrice());
            }
        };
        tokenList.setAdapter(adapter);
        tokenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onTokenItemClick(test.get(position));
            }
        });
    }

    @OnClick({R.id.mine_wallet_token_gc_special_code, R.id.mine_wallet_token_receive, 
            R.id.mine_wallet_token_transfer,R.id.mine_wallet_token_quick_exchange})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.mine_wallet_token_gc_special_code:
                presenter.onGCSpecialCodeClick();                
            break;
            case R.id.mine_wallet_token_receive:
                presenter.onTokenReceiveClick();
            break;
            case R.id.mine_wallet_token_transfer:
                presenter.onTokenTransferClick();
            break;
            case R.id.mine_wallet_token_quick_exchange:
                presenter.onQuickExchangeClick();
            break;
        
            default:
                break;
        }
    }

    @Override
    public void updateTotalMoney(String totalMoney) {
        this.totalMoney.setText(totalMoney);
    }

    @Override
    public void updateTokenList(UserCurrencyDataEntity listData) {
        List<UserCurrencyEntity> data = listData.getData();
        test.clear();
        test.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
