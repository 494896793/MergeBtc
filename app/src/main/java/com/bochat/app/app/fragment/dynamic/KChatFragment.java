package com.bochat.app.app.fragment.dynamic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.adapter.KChatHistoryAdapter;
import com.bochat.app.app.adapter.KChatTitleAdapter;
import com.bochat.app.app.view.kline.KData;
import com.bochat.app.app.view.kline.KLineView;
import com.bochat.app.common.contract.dynamic.KChatContract;
import com.bochat.app.common.util.NumberUtil;
import com.bochat.app.model.bean.TradingRulesEntity;
import com.bochat.app.model.event.MarketQuotationEvent;
import com.bochat.app.model.modelImpl.MarketCenter.KLineCommand;
import com.bochat.app.model.modelImpl.MarketCenter.KLineEntity;
import com.bochat.app.model.modelImpl.MarketCenter.KLineInstantEntity;
import com.bochat.app.model.modelImpl.MarketCenter.KLineItemEntity;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity;
import com.bochat.app.model.util.NumUtils;
import com.bochat.app.mvp.view.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 2019/6/19
 * Author LDL
 **/
public class KChatFragment extends BaseFragment<KChatContract.Presenter> implements KChatContract.View, KLineView.DataMoveListener {

    @Inject
    KChatContract.Presenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.mKLineView)
    KLineView mKLineView;

    @BindView(R.id.history_recycler)
    RecyclerView history_recycler;

    @BindView(R.id.have_data_relative)
    RelativeLayout have_data_relative;

    @BindView(R.id.no_data_relative)
    RelativeLayout no_data_relative;

    @BindView(R.id.price_max)
    TextView price_max;

    @BindView(R.id.exchange_text)
    TextView exchange_text;

    @BindView(R.id.exchange_person_text)
    TextView exchange_person_text;

    @BindView(R.id.exchange_total)
    TextView exchange_total;

    @BindView(R.id.price_min)
    TextView price_min;

    @BindView(R.id.price_volume)
    TextView price_volume;

    @BindView(R.id.price_text)
    TextView price_text;

    @BindView(R.id.num_text)
    TextView num_text;

    private TransactionEntity mEntity;
    private KChatHistoryAdapter historyAdapter;
    private KChatTitleAdapter adapter;
    private List<String> titles = new ArrayList<>();
    private KChatTitleAdapter.KChatTitleViewHolder lastHolder;
    private int startId = 0;
    private int offset = 300;
    private String marketId;
    private String nowChoose = "5分钟";
    private int isFirstEnter = -1;
    private KLineEntity currentKLine;
    private int getHistory = -1;

    private List<KData> min5 = new ArrayList<>();
    private List<KData> min30 = new ArrayList<>();
    private List<KData> hour = new ArrayList<>();
    private List<KData> day = new ArrayList<>();
    private List<KData> week = new ArrayList<>();
    private List<KData> realTimeList = new ArrayList<>();
    private List<KData> recordList = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    public TransactionEntity getmEntity() {
        return mEntity;
    }

    public void setmEntity(TransactionEntity mEntity) {
        this.mEntity = mEntity;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected KChatContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kchat, null);
        return view;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initRecyclerView();
        marketId = getArguments().getString("marketId");
//        marketId="37";
        //初始化控件加载数据
        mKLineView.setDataMoveListener(this);
        //设置十字线移动模式，默认为0：固定指向收盘价
        mKLineView.setCrossHairMoveMode(KLineView.CROSS_HAIR_MOVE_OPEN);
        mKLineView.initKDataList(clearErrorData());
//        mKLineView.initKDataList(getKDataList(10));

        /**
         * 当控件显示数据属于总数据量的前三分之一时，会自动调用该接口，用于预加载数据，保证控件操作过程中的流畅性，
         * 虽然做了预加载，当总数据量较小时，也会出现用户滑到左边界了，但数据还未获取到，依然会有停顿。
         * 所以数据量越大，越不会出现停顿，也就越流畅
         */
        mKLineView.setOnRequestDataListListener(new KLineView.OnRequestDataListListener() {
            @Override
            public void requestData() {
                Log.i("==========", "====================requestData");
                //延时3秒执行，模拟网络请求耗时
//                mHandler.postDelayed(dataListAddRunnable, 500);
            }
        });
        if (mEntity != null) {
            price_text.setText("价格(" + mEntity.getBuyerName() + ")");
            num_text.setText("数量(" + mEntity.getSellerName() + ")");
        }
    }

    private void initData() {
//        titles.clear();
        min5.clear();
        min30.clear();
        hour.clear();
        week.clear();
        day.clear();
        map.clear();
        realTimeList.clear();
        recordList.clear();
        noDataViewRefresh();
        nowChoose = "5分钟";
        isFirstEnter = -1;
        startId = 0;
        offset = 300;
        addHistory(new ArrayList<KData>());
//        View view = recycler.getChildAt(0);
//        KChatTitleAdapter.KChatTitleViewHolder holder = (KChatTitleAdapter.KChatTitleViewHolder) recycler.getChildViewHolder(view);
//        holder.title_text.setTextColor(getResources().getColor(R.color.color_0084FF));
    }

    private void initRecyclerView() {
        initData();
        checkTitles();
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new KChatTitleAdapter(getActivity(), titles);
        adapter.setOnItemClickListener(new KChatTitleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(final int position, String title) {
                if (position == recycler.getChildCount() - 1) {
                    no_data_relative.setVisibility(View.VISIBLE);
                    have_data_relative.setVisibility(View.GONE);
                } else {
                    no_data_relative.setVisibility(View.GONE);
                    have_data_relative.setVisibility(View.VISIBLE);
                }
                //第一个先初始化
                View view0 = recycler.getChildAt(0);
                KChatTitleAdapter.KChatTitleViewHolder holder0 = (KChatTitleAdapter.KChatTitleViewHolder) recycler.getChildViewHolder(view0);
                holder0.title_text.setTextColor(getResources().getColor(R.color.color_999999));

                View view = recycler.getChildAt(position);
                if (lastHolder != null) {
                    lastHolder.title_text.setTextColor(getResources().getColor(R.color.color_999999));
                }
                KChatTitleAdapter.KChatTitleViewHolder holder = (KChatTitleAdapter.KChatTitleViewHolder) recycler.getChildViewHolder(view);
                holder.title_text.setTextColor(getResources().getColor(R.color.color_0084FF));
                lastHolder = holder;
                if (!nowChoose.equals(title)) {
                    noDataViewRefresh();
                    startId = 0;
                    offset = 300;
                    isFirstEnter = -1;
                    realTimeList.clear();
                    recordList.clear();
                    nowChoose = title;
                    getChooseData(title, false);

                }
                nowChoose = title;
            }
        });
        nowChoose = "5分钟";
        recycler.setAdapter(adapter);

        historyAdapter = new KChatHistoryAdapter(getActivity(), new ArrayList<KLineInstantEntity.TradeItemEntity>(), null);
        history_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        history_recycler.setAdapter(historyAdapter);
    }

    private void noDataViewRefresh() {
        List<KData> refresh = new ArrayList<>();
        KData kData = new KData();
        kData.setClosePrice(0);
        kData.setOpenPrice(0);
        kData.setMinPrice(0);
        kData.setMaxPrice(0);
        refresh.add(kData);
        mKLineView.initKDataList(refresh);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //退出页面时停止子线程并置空，便于回收，避免内存泄露
        mKLineView.cancelQuotaThread();
        initData();

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.queryTradingRules(Long.valueOf(marketId));
    }

    @Subscribe
    public void onReceiverMessage(MarketQuotationEvent event) {
        price_text.setText("价格(" + event.getEntity().getBuyerName() + ")");
        num_text.setText("数量(" + event.getEntity().getSellerName() + ")");
        this.marketId = event.getEntity().getMarketId();
        try {
            presenter.queryTradingRules(Long.valueOf(marketId));
        } catch (Exception e) {
        }
        initData();
        getChooseData(nowChoose, false);
        presenter.sendInstantOrder(marketId, KLineCommand.KLineType.INSTANT, startId, offset);
        try {
            if (lastHolder != null) {
                lastHolder.title_text.setTextColor(getResources().getColor(R.color.color_999999));
            }
            View view = recycler.getChildAt(0);
            KChatTitleAdapter.KChatTitleViewHolder holder = (KChatTitleAdapter.KChatTitleViewHolder) recycler.getChildViewHolder(view);
            holder.title_text.setTextColor(getResources().getColor(R.color.color_0084FF));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<KData> dataFormate(List<KLineItemEntity> items) {
        List<KData> list = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            KData kData = new KData();
            if (TextUtils.isEmpty(items.get(i).getStart()))
                kData.setOpenPrice(Double.valueOf(items.get(i).getStart()));
            kData.setClosePrice(Double.valueOf(items.get(i).getEnd()));
            kData.setMaxPrice(Double.valueOf(items.get(i).getMax()));
            kData.setMinPrice(Double.valueOf(items.get(i).getMin()));
//            kData.setVolume(Double.valueOf(items.get(i).get));
        }
        return list;
    }

    private void updateTrade(KLineEntity kLineEntity) {
        if (kLineEntity.getInstantEntity().getTradeItems().size() != 0) {
            no_data_relative.setVisibility(View.GONE);
            have_data_relative.setVisibility(View.VISIBLE);
            historyAdapter.refreshData(kLineEntity.getInstantEntity().getTradeItems(), mTradingRulesEntity);
        } else {
            no_data_relative.setVisibility(View.VISIBLE);
            have_data_relative.setVisibility(View.GONE);
        }
    }

    private void updateTop(KLineEntity kLineEntity) {
        setTextWithRule(exchange_text, NumberUtil.parseDouble(kLineEntity.getInstantEntity().getFinalPrice()));
        if (!TextUtils.isEmpty(kLineEntity.getInstantEntity().getRateToRMB())) {
            exchange_total.setText("≈ ￥" + NumUtils.CointNum(Double.valueOf(kLineEntity.getInstantEntity().getRateToRMB()), 2));
        }


        String rangRates = kLineEntity.getInstantEntity().getRate();
        //查找是否有符号
        char symbol = rangRates.charAt(0);

        String replacedRate = "";
        StringBuilder rateBuilder = new StringBuilder();
        //是否有+，-
        if (symbol == '0') {
            //是否有% 去除百分号
            if (rangRates.indexOf("%") > 0)
                replacedRate = rangRates
                        .replaceAll("%", "");
        } else {

            String symbolStr = String.valueOf(symbol);
            //去除+，-，%
            replacedRate = rangRates
                    .replaceAll("\\+", "")
                    .replaceAll("-", "")
                    .replaceAll("%", "");
            replacedRate = symbolStr + replacedRate;
        }
        //转换成double类型
        double formatRate = NumberUtil.parseDouble(replacedRate);
        if(formatRate>0){
            exchange_text.setTextColor(getResources().getColor(R.color.quotation_green));
            exchange_person_text.setTextColor(getResources().getColor(R.color.quotation_green));
        }else{
            exchange_text.setTextColor(getResources().getColor(R.color.quotation_red_s));
            exchange_person_text.setTextColor(getResources().getColor(R.color.quotation_red_s));
        }
        //格式化保留两位的字符串
        String rateString = getViewContext().getString(R.string.kchat_rate_fmt, formatRate);
        exchange_person_text.setText(rateString);

        setTextWithRule(price_max, NumberUtil.parseDouble(kLineEntity.getInstantEntity().getMaxPrice24H()));
        setTextWithRule(price_min, NumberUtil.parseDouble(kLineEntity.getInstantEntity().getMinPrice24H()));
        double v = NumberUtil.parseDouble(kLineEntity.getInstantEntity().getAmplitude24H());
        price_volume.setText(String.format("%.4f", v));
    }

    private void updateMA(KLineEntity kLineEntity) {
        checkTitles();
//        if (nowChoose.equals(titles.get(0))) {
//            mKLineView.setTopMa("MA:" + kLineEntity.getInstantEntity().getMin5().getMaxValue1(), "MA:" + kLineEntity.getInstantEntity().getMin5().getMaxValue2(), "MA:" + kLineEntity.getInstantEntity().getMin5().getMaxValue3());
//            mKLineView.setBottomMa("MA:" + kLineEntity.getInstantEntity().getMin5().getMaxValue1(), "MA:" + kLineEntity.getInstantEntity().getMin5().getMaxValue2(), "MA:" + kLineEntity.getInstantEntity().getMin5().getMaxValue3());
//        } else if (nowChoose.equals(titles.get(1))) {
//            mKLineView.setTopMa("MA:" + kLineEntity.getInstantEntity().getMin30().getMaxValue1(), "MA:" + kLineEntity.getInstantEntity().getMin30().getMaxValue2(), "MA:" + kLineEntity.getInstantEntity().getMin30().getMaxValue3());
//            mKLineView.setBottomMa("MA:" + kLineEntity.getInstantEntity().getMin30().getMaxValue1(), "MA:" + kLineEntity.getInstantEntity().getMin30().getMaxValue2(), "MA:" + kLineEntity.getInstantEntity().getMin30().getMaxValue3());
//        } else if (nowChoose.equals(titles.get(2))) {
//            mKLineView.setTopMa("MA:" + kLineEntity.getInstantEntity().getHour().getMaxValue1(), "MA:" + kLineEntity.getInstantEntity().getHour().getMaxValue2(), "MA:" + kLineEntity.getInstantEntity().getHour().getMaxValue3());
//            mKLineView.setBottomMa("MA:" + kLineEntity.getInstantEntity().getHour().getMaxValue1(), "MA:" + kLineEntity.getInstantEntity().getHour().getMaxValue2(), "MA:" + kLineEntity.getInstantEntity().getHour().getMaxValue3());
//        } else if (nowChoose.equals(titles.get(3))) {
//            mKLineView.setTopMa("MA:" + kLineEntity.getInstantEntity().getDay().getMaxValue1(), "MA:" + kLineEntity.getInstantEntity().getDay().getMaxValue2(), "MA:" + kLineEntity.getInstantEntity().getDay().getMaxValue3());
//            mKLineView.setBottomMa("MA:" + kLineEntity.getInstantEntity().getDay().getMaxValue1(), "MA:" + kLineEntity.getInstantEntity().getDay().getMaxValue2(), "MA:" + kLineEntity.getInstantEntity().getDay().getMaxValue3());
//        } else {
//            mKLineView.setTopMa("MA:" + kLineEntity.getInstantEntity().getWeek().getMaxValue1(), "MA:" + kLineEntity.getInstantEntity().getWeek().getMaxValue2(), "MA:" + kLineEntity.getInstantEntity().getWeek().getMaxValue3());
//            mKLineView.setBottomMa("MA:" + kLineEntity.getInstantEntity().getWeek().getMaxValue1(), "MA:" + kLineEntity.getInstantEntity().getWeek().getMaxValue2(), "MA:" + kLineEntity.getInstantEntity().getWeek().getMaxValue3());
//        }
    }

    private KData makeKData(KLineItemEntity itemEntity) {
        KData kData = null;
        if (!itemEntity.getNum().equals("0")) {
            kData = new KData();
            kData.setId(itemEntity.getId());
            kData.setMaxPrice(Double.valueOf(itemEntity.getMax()));
            kData.setMinPrice(Double.valueOf(itemEntity.getMin()));
            kData.setOpenPrice(Double.valueOf(itemEntity.getStart()));
            kData.setClosePrice(Double.valueOf(itemEntity.getEnd()));
            kData.setVolume(Double.valueOf(itemEntity.getNum()));
            kData.setTime(itemEntity.getCreateTimeStamp());
        }
        return kData;
    }

    @Override
    public void getData(final KLineEntity kLineEntity, final boolean isFind) {
        Log.i("=======", "=======kLineEntity:" + kLineEntity.toString());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //实时数据
                if (kLineEntity.getkLineType() == KLineCommand.KLineType.INSTANT) {
                    if (kLineEntity.getInstantEntity() != null) {

                        currentKLine = kLineEntity;

                        updateTrade(kLineEntity);

                        updateTop(kLineEntity);

                        updateMA(kLineEntity);

                        if (kLineEntity.getInstantEntity().getMin5() != null) {
                            KData kData;
                            checkTitles();
                            if (nowChoose.equals(titles.get(0))) {
                                kData = makeKData(kLineEntity.getInstantEntity().getMin5());
                                if (kData != null) {
                                    min5.add(kData);
                                }
                                initChangeData(min5);
//                                if (isFirstEnter == -1 && realTimeList.size() == 0) {
                                startId = (int) kLineEntity.getInstantEntity().getMin5().getId();
//                                    presenter.get5MINData(startId, offset, marketId, false);
//                                }
                            } else if (nowChoose.equals(titles.get(1))) {
                                kData = makeKData(kLineEntity.getInstantEntity().getMin30());
                                if (kData != null) {
                                    min30.add(kData);
                                }
                                initChangeData(min30);
//                                if (isFirstEnter == -1 && realTimeList.size() == 0) {
                                startId = (int) kLineEntity.getInstantEntity().getMin30().getId();
//                                    presenter.get30MINData(startId, offset, marketId, false);
//                                }
                            } else if (nowChoose.equals(titles.get(2))) {
                                kData = makeKData(kLineEntity.getInstantEntity().getHour());
                                if (kData != null) {
                                    hour.add(kData);
                                }
                                initChangeData(hour);
                                if (isFirstEnter == -1 && realTimeList.size() == 0) {
                                    startId = (int) kLineEntity.getInstantEntity().getHour().getId();
//                                    presenter.getHOURData(startId, offset, marketId, false);
                                }
                            } else if (nowChoose.equals(titles.get(3))) {
                                kData = makeKData(kLineEntity.getInstantEntity().getDay());
                                if (kData != null) {
                                    day.add(kData);
                                }
                                initChangeData(day);
//                                if (isFirstEnter == -1 && realTimeList.size() == 0) {
                                startId = (int) kLineEntity.getInstantEntity().getDay().getId();
//                                    presenter.getDAYData(startId, offset, marketId, false);
//                                }
                            } else {
                                kData = makeKData(kLineEntity.getInstantEntity().getWeek());
                                if (kData != null) {
                                    week.add(kData);
                                }
                                initChangeData(week);
//                                if (isFirstEnter == -1 && realTimeList.size() == 0) {
                                startId = (int) kLineEntity.getInstantEntity().getWeek().getId();
//                                    presenter.getWEEKData(startId, offset, marketId, false);
//                                }
                            }

//                            if(kData!=null){
//                                if(realTimeList.isEmpty()){
//                                    realTimeList.add(kData);
//                                } else {
//                                    KData last = realTimeList.get(realTimeList.size() - 1);
//                                    if(kData.getId() > last.getId()){
//                                        realTimeList.add(realTimeList.size() - 1, kData);
//                                    }
//                                }
//                            }
                        }

//                        if (realTimeList.size() != 0) {
//                            if (isFirstEnter != -1) {
//                                if (mKLineView.getData() == null || mKLineView.getData().size() == 0) {
//                                    mKLineView.initKDataList(realTimeList);
//                                } else {
//                                    mKLineView.resetDataList(clearErrorData(), true);
//                                }
//                            } else {
//                                mKLineView.initKDataList(clearErrorData());
//                            }
//                        }
                    } else {
                        if (historyAdapter.getData() == null || historyAdapter.getData().size() == 0) {
                            no_data_relative.setVisibility(View.VISIBLE);
                            have_data_relative.setVisibility(View.GONE);
                        }
                    }
//                        if (kLineEntity.getListEntity() != null) {
//                            if (!TextUtils.isEmpty(kLineEntity.getListEntity().getStartId())) {
//                                startId = Integer.valueOf(kLineEntity.getListEntity().getStartId());
//                            }
//                        }
                    //历史记录
//                    mKLineView.initKDataList(getKDataList(10));
                } else {
                    isFirstEnter = 1;
                    recordList = new ArrayList<>();
                    if (kLineEntity.getListEntity() != null && kLineEntity.getListEntity().getItems() != null && kLineEntity.getListEntity().getItems().size() != 0) {
                        List<KLineItemEntity> items = kLineEntity.getListEntity().getItems();
                        int total = mKLineView.getTotleSize();
                        for (int i = 0; i < items.size(); i++) {
                            //todo wangyufei
                            if (!items.get(i).getNum().equals("0")) {
                                KData kData = new KData();
                                kData.setId(items.get(i).getId());
                                kData.setOpenPrice(Double.valueOf(items.get(i).getStart()));
                                kData.setClosePrice(Double.valueOf(items.get(i).getEnd()));
                                kData.setMaxPrice(Double.valueOf(items.get(i).getMax()));
                                kData.setMinPrice(Double.valueOf(items.get(i).getMin()));
                                kData.setTime(items.get(i).getCreateTimeStamp());
                                kData.setVolume(Double.valueOf(items.get(i).getNum()));
                                recordList.add(kData);
                            }
                        }
                        addHistory(recordList);
//                        if (total == 0) {
//                            mKLineView.initKDataList(recordList);
//                        } else {
//                            if (recordList.size() != 0) {
//                                mKLineView.resetDataList(clearErrorData(), true);
//                            }
//                        }
                    }
//                    mKLineView.initKDataList(getKDataList(10));
                }
            }

        });
    }

    private void checkTitles() {
        if (titles == null || titles.size() == 0) {
            titles.add("5分钟");
            titles.add("30分钟");
            titles.add("1小时");
            titles.add("1天");
            titles.add("1周");
        }
    }

    private void addHistory(List<KData> list) {
        checkTitles();
        if (!map.containsKey(nowChoose)) {
            if (list.size() != 0) {
                map.put(nowChoose, nowChoose);
            }
            refreshKLine(list);
        }
    }

    private void refreshKLine(List<KData> list) {
        if (nowChoose.equals(titles.get(0))) {
            min5.addAll(0, list);
            initChangeData(min5);
        } else if (nowChoose.equals(titles.get(1))) {
            min30.addAll(0, list);
            initChangeData(min30);
        } else if (nowChoose.equals(titles.get(2))) {
            hour.addAll(0, list);
            initChangeData(hour);
        } else if (nowChoose.equals(titles.get(3))) {
            day.addAll(0, list);
            initChangeData(day);
        } else {
            week.addAll(0, list);
            initChangeData(week);
        }
    }

    private void initChangeData(List<KData> list) {
        if (list.size() == 0) {
            noDataViewRefresh();
        } else {
            mKLineView.resetDataList(list, true);
        }
    }

    private List<KData> clearErrorData() {
        List<KData> newDatas = new ArrayList<>();
        if (recordList.size() == 0 && realTimeList.size() == 0) {

        } else if (recordList.size() != 0 && realTimeList.size() == 0) {
            newDatas = recordList;

        } else if (recordList.size() == 0) {
            newDatas = realTimeList;

        } else {
            newDatas.addAll(recordList);
            newDatas.addAll(realTimeList);
        }

        if (isFirstEnter == -1) {
            newDatas = new ArrayList<>();
            KData kData = new KData();
            kData.setMaxPrice(0);
            kData.setMinPrice(0);
            kData.setOpenPrice(0);
            kData.setClosePrice(0);
            newDatas.add(kData);
        }

        Log.i("=========", "=========newDatas:" + newDatas.toString());
        return newDatas;
    }


    private void getChooseData(String title, boolean isFind) {
//        if(currentKLine == null){
//            presenter.sendInstantOrder(marketId,KLineCommand.KLineType.INSTANT,0,300);
//            return;
//        }
        presenter.sendInstantOrder(marketId, KLineCommand.KLineType.INSTANT, 0, 300);
        if (map.get(nowChoose) == null) {
            startId = 0;
            offset = 300;
            if (title.equals("5分钟")) {
                presenter.get5MINData(startId, offset, marketId, isFind);
            } else if (title.equals("30分钟")) {
                presenter.get30MINData(startId, offset, marketId, isFind);
            } else if (title.equals("1小时")) {
                presenter.getHOURData(startId, offset, marketId, isFind);
            } else if (title.equals("1天")) {
                presenter.getDAYData(startId, offset, marketId, isFind);
            } else {
                presenter.getWEEKData(startId, offset, marketId, isFind);
            }
        } else {
            refreshKLine(new ArrayList<KData>());
        }
    }


    @Override
    public void onDataMove(boolean mLeftMove, boolean mRightMove) {
    }

    TradingRulesEntity mTradingRulesEntity;

    @Override
    public void getTradingRulesEntity(TradingRulesEntity entity) {
        mTradingRulesEntity = entity;

        setTextWithRule(exchange_text, NumberUtil.parseDouble(exchange_text.getText().toString()));
        setTextWithRule(price_max, NumberUtil.parseDouble(price_max.getText().toString()));
        setTextWithRule(price_min, NumberUtil.parseDouble(price_min.getText().toString()));
    }

    private void setTextWithRule(TextView textview, double num) {
        if (mTradingRulesEntity != null) {
            textview.setText(String.format("%." + mTradingRulesEntity.getDecimalNum() + "f", num));
        } else {
            textview.setText(String.format("%." + 8 + "f", num));
        }
    }


    /*模拟K线数据*/
    private List<KData> getKDataList(double num) {
        long start = System.currentTimeMillis();

        Random random = new Random();
        List<KData> dataList = new ArrayList<>();
        double openPrice = 100;
        double closePrice;
        double maxPrice;
        double minPrice;
        double volume;

        for (int x = 0; x < num * 10; x++) {
            for (int i = 0; i < 12; i++) {
                start += 60 * 1000 * 5;
                closePrice = openPrice + getAddRandomDouble();
                maxPrice = closePrice + getAddRandomDouble();
                minPrice = openPrice - getSubRandomDouble();
                volume = random.nextInt(100) * 1000 + random.nextInt(10) * 10 + random.nextInt(10) + random.nextDouble();
                dataList.add(new KData(start, openPrice, closePrice, maxPrice, minPrice, volume));
                openPrice = closePrice;
            }

            for (int i = 0; i < 8; i++) {
                start += 60 * 1000 * 5;
                closePrice = openPrice - getSubRandomDouble();
                maxPrice = openPrice + getAddRandomDouble();
                minPrice = closePrice - getSubRandomDouble();
                volume = random.nextInt(100) * 1000 + random.nextInt(10) * 10 + random.nextInt(10) + random.nextDouble();
                dataList.add(new KData(start, openPrice, closePrice, maxPrice, minPrice, volume));
                openPrice = closePrice;
            }
        }
        long end = System.currentTimeMillis();
        return dataList;
    }

    private double getAddRandomDouble() {
        Random random = new Random();
        return random.nextInt(5) * 5 + random.nextDouble();
    }

    private double getSubRandomDouble() {
        Random random = new Random();
        return random.nextInt(5) * 5 - random.nextDouble();
    }
}
