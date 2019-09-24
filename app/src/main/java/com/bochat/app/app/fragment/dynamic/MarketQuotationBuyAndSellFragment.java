package com.bochat.app.app.fragment.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.adapter.MarketQuotationBSAdapter;
import com.bochat.app.app.util.DecimalFormatter;
import com.bochat.app.app.util.MoneyInputLimit;
import com.bochat.app.common.contract.dynamic.MarketQuotationBuyAndSellContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterWalletGY;
import com.bochat.app.common.util.NumberUtil;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.ResidueAmountListEntity;
import com.bochat.app.model.bean.TradingRulesEntity;
import com.bochat.app.model.event.MarketQuotationEvent;
import com.bochat.app.model.modelImpl.MarketCenter.EntrustEntity;
import com.bochat.app.model.modelImpl.MarketCenter.EntrustListEntity;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity;
import com.bochat.app.mvp.view.BaseFragment;
import com.bochat.app.mvp.view.ResultTipsType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MarketQuotationBuyAndSellFragment extends BaseFragment<MarketQuotationBuyAndSellContract.Presenter> implements
        MarketQuotationBuyAndSellContract.View,
        RadioGroup.OnCheckedChangeListener,
        MarketQuotationBSAdapter.OnItemClickListener {

    private static final String DEFAULT_NUM = "0.00000000";

    @Inject
    MarketQuotationBuyAndSellContract.Presenter presenter;

    /**
     * 百分比
     */
    @BindView(R.id.dynamic_buy_radio_group)
    RadioGroup mDynamicRadioGroup;

    /**
     * 卖出列表
     */
    @BindView(R.id.dynamic_right_bs_list_sell)
    RecyclerView mDynamicSellRecyclerView;

    /**
     * 买入列表
     */
    @BindView(R.id.dynamic_right_bs_list_buy)
    RecyclerView mDynamicBuyRecyclerView;

    /**
     * 点击买入或卖出
     */
    @BindView(R.id.dynamic_buy_or_sell_btn)
    Button mDynamicSellButton;

    /**
     * 买入或卖出单价
     */
    @BindView(R.id.dynamic_bs_price_edit)
    EditText mDynamicBSPriceEdit;

    /**
     * 买入或卖出单价右侧币种
     */
    @BindView(R.id.dynamic_bs_price_edit_right)
    TextView mDynamicBSPriceEditRight;

    /**
     * 买入或卖出数量
     */
    @BindView(R.id.dynamic_bs_count_edit)
    EditText mDynamicBSCountEdit;

    /**
     * 买入或卖出数量右侧币种
     */
    @BindView(R.id.dynamic_bs_count_edit_right)
    TextView mDynamicBSCountEditLeft;

    /**
     * 预计交易额
     */
    @BindView(R.id.dynamic_expected_turnover_result)
    TextView mDynamicExpectedTurnoverResult;

    @BindView(R.id.dynamic_balance_currencyR_left_txt)
    TextView mDynamicBalanceCurrencyRLText;

    /**
     * 右侧币种资金
     */
    @BindView(R.id.dynamic_balance_currencyR_right_txt)
    TextView mDynamicBalanceCurrencyR;

    /**
     * 左侧币种资金显示币种名称
     */
    @BindView(R.id.dynamic_balance_currencyL_left_txt)
    TextView mDynamicBalanceCurrencyLLText;

    /**
     * 左侧币种资金
     */
    @BindView(R.id.dynamic_balance_currencyL_right_txt)
    TextView mDynamicBalanceCurrencyL;

    /**
     * 可买数量显示
     */
    @BindView(R.id.dynamic_can_buy_text)
    TextView mDynamicCanBuyText;

    @BindView(R.id.dynamic_bs_right_entrust_count)
    TextView mDynamicREntrustCount;

    @BindView(R.id.dynamic_bs_right_entrust_cny_price)
    TextView mDynamicREntrustCnyPrice;

    @BindView(R.id.dynamic_balance_currencyR_right_progress)
    ProgressBar mBalanceCurrencyRProgress;
    @BindView(R.id.dynamic_balance_currencyL_right_progress)
    ProgressBar mBalanceCurrencyLProgress;

    /**
     * 是否卖出或买入标记
     */
    private boolean isSell = false;

    /**
     * 传入类型
     */
    private String mType;

    /**
     * 格式化
     */
    private String mDecimalFormat = "###,###" + DEFAULT_NUM;

    /**
     * 记录可买或可卖出数量
     */
    private double mCanBuyOrSellCount = 0.00000000;

    private TradingRulesEntity mTradingRulesEntity;
    /**
     * 传入实体
     */
    private TransactionEntity mEntity;

    private MarketQuotationBSAdapter mBuyAdapter;
    private MarketQuotationBSAdapter mSellAdapter;

    @Override
    protected void initWidget() {
        super.initWidget();

        mDynamicBSPriceEdit.setHint(isSell ? getString(R.string.market_quotation_bs_price, "卖出") : getString(R.string.market_quotation_bs_price, "买入"));
        mDynamicBSCountEdit.setHint(isSell ? getString(R.string.market_quotation_bs_count, "卖出") : getString(R.string.market_quotation_bs_count, "买入"));
        mDynamicSellButton.setText(isSell ? "卖出" : "买入");
        mDynamicSellButton.setBackgroundColor(getViewContext().getResources().getColor(isSell ? R.color.quotation_red_s : R.color.quotation_green));

        for (int i = 0; i < mDynamicRadioGroup.getChildCount(); i++) {
            View children = mDynamicRadioGroup.getChildAt(i);
            if (children instanceof RadioButton) {
                children.setBackground(isSell ? getViewContext().getResources().getDrawable(R.drawable.market_quotation_radio_sell_selector)
                        : getViewContext().getResources().getDrawable(R.drawable.market_quotation_radio_buy_selector));
            }
        }


        if (mEntity != null) {

            mDynamicBSPriceEditRight.setText(mEntity.getBuyerName());
            mDynamicBSCountEditLeft.setText(mEntity.getSellerName());

            mDynamicBalanceCurrencyRLText.setText(getViewContext().getResources().getString(R.string.market_quotation_blance_left_fmt, mEntity.getBuyerName()));
            mDynamicBalanceCurrencyLLText.setText(getViewContext().getResources().getString(R.string.market_quotation_blance_left_fmt, mEntity.getSellerName()));

            mDynamicExpectedTurnoverResult.setText(getViewContext().getString(R.string.market_quotation_expected_turnover_result, DEFAULT_NUM, mEntity.getBuyerName()));
            mDynamicCanBuyText.setText(getViewContext().getString(isSell ? R.string.market_quotation_can_ss_fmt
                    : R.string.market_quotation_can_bs_fmt, mEntity.getSellerName(), DEFAULT_NUM));
        }

        mDynamicRadioGroup.setOnCheckedChangeListener(this);
        mDynamicBSPriceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String sPrice = mDynamicBSPriceEdit.getText().toString();
                String sBalance = mDynamicBalanceCurrencyR.getText().toString();

                String sellerName = mEntity.getSellerName();

                mDynamicBSCountEdit.setText("");

                if (!TextUtils.isEmpty(sBalance)) {
                    if (!isSell) {
                        double dResidue = NumberUtil.parseDouble(sBalance);
                        if (dResidue > 0 && !TextUtils.isEmpty(sPrice)) {
                            double dSellerPrice = NumberUtil.parseDouble(sPrice);
                            if (dSellerPrice > dResidue) {
                                showTips(new ResultTipsType("超出可用资产", false));
                            } else {
                                if (dSellerPrice > 0) {
                                    mCanBuyOrSellCount = (dResidue / dSellerPrice);
                                } else {
                                    mCanBuyOrSellCount = 0.00000000;
                                }
                                mDynamicCanBuyText.setText(getViewContext().getString(isSell ? R.string.market_quotation_can_ss_fmt
                                                : R.string.market_quotation_can_bs_fmt, sellerName,
                                        DecimalFormatter.newInstance().format(mDecimalFormat, mCanBuyOrSellCount)));

                            }

                        } else {
                            mCanBuyOrSellCount = 0.00000000;
                            mDynamicCanBuyText.setText(getViewContext().getString(isSell ? R.string.market_quotation_can_ss_fmt
                                            : R.string.market_quotation_can_bs_fmt, sellerName,
                                    DecimalFormatter.newInstance().format(mDecimalFormat, mCanBuyOrSellCount)));
                        }
                    }
//                    else {
//                        mCanBuyOrSellCount = NumberUtil.parseDouble(mDynamicBalanceCurrencyL.getText().toString());
//                        mDynamicCanBuyText.setText(getViewContext().getString(isSell ? R.string.market_quotation_can_ss_fmt
//                                        : R.string.market_quotation_can_bs_fmt, sellerName,
//                                DecimalFormatter.newInstance().format(mDecimalFormat, mCanBuyOrSellCount)));
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDynamicBSCountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String sCount = mDynamicBSCountEdit.getText().toString();
                String sPrice = mDynamicBSPriceEdit.getText().toString();

                if (!TextUtils.isEmpty(sCount) && !sCount.equals("")) {

                    double dCount = NumberUtil.parseDouble(sCount);

                    if (dCount > 0 && !TextUtils.isEmpty(sPrice)) {
                        double dPrice = NumberUtil.parseDouble(sPrice);
                        if (dCount > mCanBuyOrSellCount) {
                            String countTips = DecimalFormatter.newInstance().scaleOfRules(mCanBuyOrSellCount, mTradingRulesEntity.getCurrencyDecimalNum(), RoundingMode.HALF_EVEN).toPlainString();
                            showTips(new ResultTipsType(isSell ? "不能超过可卖数量" + countTips : "不能超过可买数量" + countTips, false));
                            mDynamicBSCountEdit.setText("");
                        } else {
                            mDynamicExpectedTurnoverResult.setText(getViewContext().getString(R.string.market_quotation_expected_turnover_result,
                                    String.valueOf(DecimalFormatter.newInstance().format(mDecimalFormat, dPrice * dCount)),
                                    mEntity.getBuyerName()));
                        }

                    } else if (dCount != 0)
                        mDynamicBSCountEdit.setText("0");


                } else {
                    mDynamicRadioGroup.clearCheck();
                    mDynamicExpectedTurnoverResult.setText(getViewContext().getString(R.string.market_quotation_expected_turnover_result,
                            DEFAULT_NUM,
                            mEntity.getBuyerName()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSellAdapter = new MarketQuotationBSAdapter(getViewContext(), true);
        mBuyAdapter = new MarketQuotationBSAdapter(getViewContext(), false);
        setRecyclerView(mDynamicSellRecyclerView, mSellAdapter, true);
        setRecyclerView(mDynamicBuyRecyclerView, mBuyAdapter, false);

    }

    private void setRecyclerView(RecyclerView recyclerView, MarketQuotationBSAdapter adapter, boolean isReverse) {
        LinearLayoutManager manager = new LinearLayoutManager(getViewContext());
        manager.setReverseLayout(isReverse);
        adapter.setOnItemClickListener(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onUpdateMessage(MarketQuotationEvent event) {
        mEntity = event.getEntity();
        initWidget();
        presenter.update(mEntity);
    }


    public void setType(String type) {
        mType = type;
    }

    public void setEntity(TransactionEntity entity) {
        mEntity = entity;
    }

    public void isSell(boolean sell) {
        isSell = sell;
    }

    public void sendMessage() {
        presenter.update(mEntity);
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected MarketQuotationBuyAndSellContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_market_quotation_buy_and_sell_layout, container, false);
    }

    @Override
    public void getResidueAmount(ResidueAmountListEntity entity) {

        mBalanceCurrencyLProgress.setVisibility(View.GONE);
        mBalanceCurrencyRProgress.setVisibility(View.GONE);
        if (entity != null && entity.getData().size() > 0) {

            if (mEntity.getBuyerId().equals(entity.getData().get(0).getCurrency_id())) {
                String lAmount = entity.getData().get(1).getResidue_amount();
                String rAmount = entity.getData().get(0).getResidue_amount();
                if (mTradingRulesEntity != null) {
                    BigDecimal lAmountDecimal = DecimalFormatter.newInstance().scaleOfRules(NumberUtil.parseDouble(lAmount),
                            mTradingRulesEntity.getCurrencyDecimalNum(),
                            RoundingMode.HALF_EVEN);
                    BigDecimal rAmountDecimal = DecimalFormatter.newInstance().scaleOfRules(NumberUtil.parseDouble(rAmount),
                            mTradingRulesEntity.getCurrencyDecimalNum(),
                            RoundingMode.HALF_EVEN);
                    lAmount = lAmountDecimal.doubleValue() == 0 ? DEFAULT_NUM : lAmountDecimal.toPlainString();
                    rAmount = rAmountDecimal.doubleValue() == 0 ? DEFAULT_NUM : rAmountDecimal.toPlainString();
                }
                mDynamicBalanceCurrencyL.setText(lAmount);
                mDynamicBalanceCurrencyR.setText(rAmount);
            } else {
                String lAmount = entity.getData().get(0).getResidue_amount();
                String rAmount = entity.getData().get(1).getResidue_amount();
                if (mTradingRulesEntity != null) {
                    BigDecimal lAmountDecimal = DecimalFormatter.newInstance().scaleOfRules(NumberUtil.parseDouble(lAmount),
                            mTradingRulesEntity.getCurrencyDecimalNum(),
                            RoundingMode.HALF_EVEN);
                    BigDecimal rAmountDecimal = DecimalFormatter.newInstance().scaleOfRules(NumberUtil.parseDouble(rAmount),
                            mTradingRulesEntity.getCurrencyDecimalNum(),
                            RoundingMode.HALF_EVEN);
                    lAmount = lAmountDecimal.doubleValue() == 0 ? DEFAULT_NUM : lAmountDecimal.toPlainString();
                    rAmount = rAmountDecimal.doubleValue() == 0 ? DEFAULT_NUM : rAmountDecimal.toPlainString();
                }
                mDynamicBalanceCurrencyL.setText(lAmount);
                mDynamicBalanceCurrencyR.setText(rAmount);
            }
        } else {
            mDynamicBalanceCurrencyL.setText(DEFAULT_NUM);
            mDynamicBalanceCurrencyR.setText(DEFAULT_NUM);
        }
        //如果是卖出，则直接显示可卖数量为当前数量
        if (isSell) {
            mCanBuyOrSellCount = NumberUtil.parseDouble(mDynamicBalanceCurrencyL.getText().toString());
            mDynamicCanBuyText.setText(getViewContext().getString(isSell ? R.string.market_quotation_can_ss_fmt
                            : R.string.market_quotation_can_bs_fmt, mEntity.getSellerName(),
                    DecimalFormatter.newInstance().format(mDecimalFormat, mCanBuyOrSellCount)));
        }
    }

    @Override
    public void errorResidueAmount() {
        mBalanceCurrencyLProgress.setVisibility(View.GONE);
        mBalanceCurrencyRProgress.setVisibility(View.GONE);
        mDynamicBalanceCurrencyL.setText(DEFAULT_NUM);
        mDynamicBalanceCurrencyR.setText(DEFAULT_NUM);
    }

    @Override
    public void updateEntrustList(final EntrustListEntity entity) {
        mDynamicBuyRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mEntity != null) {
                    if (mTradingRulesEntity != null) {
                        mDynamicREntrustCount.setText(getViewContext().getString(R.string.cny_price_fmt,
                                DecimalFormatter
                                        .newInstance()
                                        .scaleOfRules(entity.getMakePrice(), mTradingRulesEntity.getDecimalNum(), RoundingMode.HALF_EVEN)
                                        .toPlainString(),
                                mEntity.getBuyerName()));
                    } else {
                        mDynamicREntrustCount.setText(getString(R.string.cny_price_fmt,
                                DecimalFormatter
                                        .newInstance()
                                        .scaleOfRules(entity.getMakePrice(), 4, RoundingMode.HALF_EVEN)
                                        .toPlainString(),
                                mEntity.getBuyerName()));
                    }
                    mDynamicREntrustCnyPrice.setText(getViewContext().getString(R.string.market_quotation_exchange_fmt, DecimalFormatter.newInstance().format("###,###0.00", entity.getCnyPrice())));
                    List<EntrustEntity> maxList = entity.getMaxList();
                    List<EntrustEntity> minList = entity.getMinList();
                    mBuyAdapter.notifyData(appendEntrustEntity(maxList, 10 - maxList.size(), false));
                    mSellAdapter.notifyData(appendEntrustEntity(minList, 10 - minList.size(), false));
                }
            }
        });
    }

    private List<EntrustEntity> appendEntrustEntity(List<EntrustEntity> source, int count, boolean isHead) {
        if (count <= 0) {
            return source;
        }
        List<EntrustEntity> result = new ArrayList<>();
        if (!isHead) {
            result.addAll(source);
        }
        for (int i = 0; i < count; i++) {
            try {
                EntrustEntity entrustEntity = new EntrustEntity(new JSONObject("{}"));
                result.add(entrustEntity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (isHead) {
            result.addAll(source);
        }
        return result;
    }

    @Override
    public void getTradingRulesEntity(TradingRulesEntity entity) {
        mTradingRulesEntity = entity;
        mBuyAdapter.setRules(mTradingRulesEntity);
        mSellAdapter.setRules(mTradingRulesEntity);
        mDynamicBSPriceEdit.addTextChangedListener(new MoneyInputLimit(mDynamicBSPriceEdit).setDigits(entity.getDecimalNum()));
        mDynamicBSCountEdit.addTextChangedListener(new MoneyInputLimit(mDynamicBSCountEdit).setDigits(entity.getCurrencyDecimalNum()));

        if (mTradingRulesEntity != null) {
            BigDecimal lAmountDecimal = DecimalFormatter.newInstance().scaleOfRules(NumberUtil.parseDouble(mDynamicBalanceCurrencyL.getText().toString()),
                    mTradingRulesEntity.getCurrencyDecimalNum(),
                    RoundingMode.HALF_EVEN);
            BigDecimal rAmountDecimal = DecimalFormatter.newInstance().scaleOfRules(NumberUtil.parseDouble(mDynamicBalanceCurrencyR.getText().toString()),
                    mTradingRulesEntity.getCurrencyDecimalNum(),
                    RoundingMode.HALF_EVEN);
            String lAmount = lAmountDecimal.doubleValue() == 0 ? DEFAULT_NUM : lAmountDecimal.toPlainString();
            String rAmount = rAmountDecimal.doubleValue() == 0 ? DEFAULT_NUM : rAmountDecimal.toPlainString();
            mDynamicBalanceCurrencyL.setText(lAmount);
            mDynamicBalanceCurrencyR.setText(rAmount);
        }
    }

    @Override
    public void getSaleState(CodeEntity entity) {
        int retCode = entity.getRetcode();
        if (retCode == 0)
            showTips("委托成功");
        else
            showTips(entity.getMsg());
    }

    private void calculateBuy(double percent) {

        if (!TextUtils.isEmpty(mDynamicBSPriceEdit.getText().toString()) &&mCanBuyOrSellCount > 0) {

            BigDecimal p = new BigDecimal(percent);
            BigDecimal total = new BigDecimal(mCanBuyOrSellCount);
            BigDecimal c = p.multiply(total);

            double count = c.doubleValue();
            //TODO wangyufei 更新预计成交额
            mDynamicBSCountEdit.setText(DecimalFormatter.newInstance().format("###,###.00000000", count));

        } else {
            showTips(new ResultTipsType("请输入单价", false));
            mDynamicRadioGroup.clearCheck();
        }
    }

    @OnClick({R.id.dynamic_buy_or_sell_btn, R.id.dynamic_recharge_btn})
    @Override
    protected void onViewClicked(View view) {

        if (view.getId() == R.id.dynamic_buy_or_sell_btn) {
            String sPrice = mDynamicBSPriceEdit.getText().toString();
            String sCount = mDynamicBSCountEdit.getText().toString();
            if (!TextUtils.isEmpty(sPrice) && !TextUtils.isEmpty(sCount)) {

                double iCount = NumberUtil.parseDouble(sCount.contains(",") ? sCount.replaceAll(",", "") : sCount);

                if (sPrice.equals("0")) {
                    showTips(new ResultTipsType("单价不能为0", false));
                    return;
                }

                if (iCount == 0) {
                    showTips(new ResultTipsType("数量不能为0", false));
                    return;
                }

                if (mTradingRulesEntity != null) {
                    double minNum = isSell ? NumberUtil.parseDouble(mTradingRulesEntity.getSellingMinNum())
                            : NumberUtil.parseDouble(mTradingRulesEntity.getBuyingMinNum());
                    double maxNum = isSell ? NumberUtil.parseDouble(mTradingRulesEntity.getSellingMaxNum())
                            : NumberUtil.parseDouble(mTradingRulesEntity.getBuyingMaxNum());
                    if (iCount < minNum) {
                        showTips(new ResultTipsType((isSell ? "卖出" : "买入") + "数量不能低于" + minNum, false));
                        return;
                    }
                    if (iCount > maxNum) {
                        showTips(new ResultTipsType((isSell ? "卖出" : "买入") + "数量不能高于" + maxNum, false));
                        return;
                    }
                }

                long marketId = Long.valueOf(mEntity.getMarketId());
                long sellerId = Long.valueOf(mEntity.getSellerId());
                long buyerId = Long.valueOf(mEntity.getBuyerId());

                presenter.saleBuying(
                        marketId,
                        sPrice,
                        iCount,
                        isSell ? 1 : 0,
                        sellerId,
                        buyerId);
            } else {
                showTips(new ResultTipsType("请输入单价和数量", false));
            }
        } else {
            Router.navigation(new RouterWalletGY(true));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.percent_25:
                calculateBuy(0.25);
                break;
            case R.id.percent_50:
                calculateBuy(0.5);
                break;
            case R.id.percent_75:
                calculateBuy(0.75);
                break;
            case R.id.percent_100:
                calculateBuy(1);
                break;
        }
    }

    @Override
    public void onItemClick(EntrustEntity entity, int position) {
        double dPrice = entity.getPrice();
        mDynamicBSPriceEdit.setText(new BigDecimal(dPrice).setScale(mTradingRulesEntity.getCurrencyDecimalNum(), BigDecimal.ROUND_HALF_DOWN).toPlainString());
    }
}
