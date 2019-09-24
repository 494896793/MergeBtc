package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.util.MD5Util;
import com.bochat.app.model.bean.CandyDetailDataEntity;
import com.bochat.app.model.bean.ChangeDetailDataEntity;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.CurrencyDetailDataEntity;
import com.bochat.app.model.bean.CurrencyTradingDataEntity;
import com.bochat.app.model.bean.DataEntity;
import com.bochat.app.model.bean.GCcodeDataEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.IncomeDataEntity;
import com.bochat.app.model.bean.IncomeOfTodayEntity;
import com.bochat.app.model.bean.OutPromptDataEntity;
import com.bochat.app.model.bean.TokenAmountEntity;
import com.bochat.app.model.bean.TokenExchangeResultEntity;
import com.bochat.app.model.bean.TokenListEntity;
import com.bochat.app.model.bean.TokenRateEntity;
import com.bochat.app.model.bean.TotalCurrencyEntity;
import com.bochat.app.model.bean.TradingRecordDataEntity;
import com.bochat.app.model.bean.UserCurrencyDataEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;

/**
 * 2019/4/29
 * Author LDL
 **/
public class TokenAssetModel implements ITokenAssetModel {

    @Override
    public GCcodeDataEntity getGCReceive() {
        GCcodeDataEntity gCcodeDataEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(GCcodeDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).getGCReceive(Api.getGCReceive));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            gCcodeDataEntity=httpClientEntity.getObj();
        }else{
            gCcodeDataEntity=new GCcodeDataEntity();
            gCcodeDataEntity.setCode(httpClientEntity.getCode());
            gCcodeDataEntity.setMsg(httpClientEntity.getMessage());
            gCcodeDataEntity.setRetcode(httpClientEntity.getCode());
        }
        return gCcodeDataEntity;
    }

    @Override
    public TradingRecordDataEntity tradingRecord(int start, int offset, int bid, int orderType) {
        TradingRecordDataEntity tradingRecordDataEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(TradingRecordDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).tradingRecord(Api.tradingRecord,start,offset,bid,orderType));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            tradingRecordDataEntity=httpClientEntity.getObj();
        }else{
            tradingRecordDataEntity=new TradingRecordDataEntity();
            tradingRecordDataEntity.setCode(httpClientEntity.getCode());
            tradingRecordDataEntity.setMsg(httpClientEntity.getMessage());
            tradingRecordDataEntity.setRetcode(httpClientEntity.getCode());
        }
        return tradingRecordDataEntity;
    }

    @Override
    public ChangeDetailDataEntity getChangeDetails(int id) {
        ChangeDetailDataEntity changeDetailDataEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(ChangeDetailDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).getChangeDetails(Api.getChangeDetails,id));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            changeDetailDataEntity=httpClientEntity.getObj();
        }else{
            changeDetailDataEntity=new ChangeDetailDataEntity();
            changeDetailDataEntity.setCode(httpClientEntity.getCode());
            changeDetailDataEntity.setMsg(httpClientEntity.getMessage());
            changeDetailDataEntity.setRetcode(httpClientEntity.getCode());
        }
        return changeDetailDataEntity;
    }

    @Override
    public IncomeDataEntity getIncomeSpending(int bid) {
        IncomeDataEntity incomeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(IncomeDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).getIncomeSpending(Api.getIncomeSpending,bid));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            incomeEntity=httpClientEntity.getObj();
        }else{
            incomeEntity=new IncomeDataEntity();
            incomeEntity.setCode(httpClientEntity.getCode());
            incomeEntity.setMsg(httpClientEntity.getMessage());
            incomeEntity.setRetcode(httpClientEntity.getCode());
        }
        return incomeEntity;
    }

    @Override
    public CandyDetailDataEntity getCandyDetails(int id) {
        CandyDetailDataEntity candyDetailDataEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CandyDetailDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).getCandyDetails(Api.getCandyDetails,id));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            candyDetailDataEntity=httpClientEntity.getObj();
        }else{
            candyDetailDataEntity=new CandyDetailDataEntity();
            candyDetailDataEntity.setCode(httpClientEntity.getCode());
            candyDetailDataEntity.setMsg(httpClientEntity.getMessage());
            candyDetailDataEntity.setRetcode(httpClientEntity.getCode());
        }
        return candyDetailDataEntity;
    }

    @Override
    public CodeEntity addTrunOut(double trunoutnum, String walletaddre, String tradpassw, int bid) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).addTrunOut(Api.addTrunOut,trunoutnum,walletaddre,MD5Util.lock(tradpassw),bid));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity addGCTransfer(int number, String password, int recipientUserId) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).addGCTransfer(Api.addGCTransfer,number, MD5Util.lock(password),recipientUserId));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public TotalCurrencyEntity getUserCurrencyCny() {
        TotalCurrencyEntity totalCurrencyEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(TotalCurrencyEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).getUserCurrencyCny(Api.getUserCurrencyCny));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            totalCurrencyEntity=httpClientEntity.getObj();
        }else{
            totalCurrencyEntity=new TotalCurrencyEntity();
            totalCurrencyEntity.setCode(httpClientEntity.getCode());
            totalCurrencyEntity.setMsg(httpClientEntity.getMessage());
            totalCurrencyEntity.setRetcode(httpClientEntity.getCode());
        }
        return totalCurrencyEntity;
    }

    @Override
    public OutPromptDataEntity getOutPrompt(int bid) {
        OutPromptDataEntity outPromptDataEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(OutPromptDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).getOutPrompt(Api.getOutPrompt,bid));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            outPromptDataEntity=httpClientEntity.getObj();
        }else{
            outPromptDataEntity=new OutPromptDataEntity();
            outPromptDataEntity.setCode(httpClientEntity.getCode());
            outPromptDataEntity.setMsg(httpClientEntity.getMessage());
            outPromptDataEntity.setRetcode(httpClientEntity.getCode());
        }
        return outPromptDataEntity;
    }

    @Override
    public UserCurrencyDataEntity listUserCurrency() {
        UserCurrencyDataEntity currencyEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(UserCurrencyDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).listUserCurrency(Api.listUserCurrency));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            currencyEntity=httpClientEntity.getObj();
        }else{
            currencyEntity=new UserCurrencyDataEntity();
            currencyEntity.setCode(httpClientEntity.getCode());
            currencyEntity.setMsg(httpClientEntity.getMessage());
            currencyEntity.setRetcode(httpClientEntity.getCode());
        }
        return currencyEntity;
    }

    @Override
    public DataEntity listPlatformCurrency(String userId) {
        DataEntity dataEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(DataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).listPlatformCurrency(Api.listPlatformCurrency,userId));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            dataEntity=httpClientEntity.getObj();
        }else{
            dataEntity=new DataEntity();
            dataEntity.setCode(httpClientEntity.getCode());
            dataEntity.setMsg(httpClientEntity.getMessage());
            dataEntity.setRetcode(httpClientEntity.getCode());
        }
        return dataEntity;
    }

    @Override
    public CurrencyDetailDataEntity getUserCurrencyDetails(int bid) {
        CurrencyDetailDataEntity currencyDetailDataEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CurrencyDetailDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).getUserCurrencyDetails(Api.getUserCurrencyDetails,bid));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            currencyDetailDataEntity=httpClientEntity.getObj();
        }else{
            currencyDetailDataEntity=new CurrencyDetailDataEntity();
            currencyDetailDataEntity.setCode(httpClientEntity.getCode());
            currencyDetailDataEntity.setMsg(httpClientEntity.getMessage());
            currencyDetailDataEntity.setRetcode(httpClientEntity.getCode());
        }
        return currencyDetailDataEntity;
    }

    @Override
    public CurrencyTradingDataEntity getUserCurrencyTrading(int start, int offset, int bid) {
        CurrencyTradingDataEntity currencyTradingDataEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CurrencyTradingDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).getUserCurrencyTrading(Api.getUserCurrencyTrading,start,offset,bid));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            currencyTradingDataEntity=httpClientEntity.getObj();
        }else{
            currencyTradingDataEntity=new CurrencyTradingDataEntity();
            currencyTradingDataEntity.setCode(httpClientEntity.getCode());
            currencyTradingDataEntity.setMsg(httpClientEntity.getMessage());
            currencyTradingDataEntity.setRetcode(httpClientEntity.getCode());
        }
        return currencyTradingDataEntity;
    }

    @Override
    public IncomeOfTodayEntity getIncomeOfToday(int currencyId) {
        IncomeOfTodayEntity totalCurrencyEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(IncomeOfTodayEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).getIncomeOfToday(Api.getIncomeOfToday, currencyId));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            totalCurrencyEntity=httpClientEntity.getObj();
        }else{
            totalCurrencyEntity=new IncomeOfTodayEntity();
            totalCurrencyEntity.setCode(httpClientEntity.getCode());
            totalCurrencyEntity.setMsg(httpClientEntity.getMessage());
            totalCurrencyEntity.setRetcode(httpClientEntity.getCode());
        }
        return totalCurrencyEntity;
    }

    
    @Override
    public TokenRateEntity getTokenRate(String sourceToken, String targetToken) {
        TokenRateEntity totalCurrencyEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                .sends(TokenRateEntity.class,"data", HttpClient.getInstance().retrofit().create(RetrofitService.class)
                        .getTokenRate(Api.getTokenRate, sourceToken, targetToken));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            totalCurrencyEntity=httpClientEntity.getObj();
        }else{
            totalCurrencyEntity=new TokenRateEntity();
            totalCurrencyEntity.setCode(httpClientEntity.getCode());
            totalCurrencyEntity.setMsg(httpClientEntity.getMessage());
            totalCurrencyEntity.setRetcode(httpClientEntity.getCode());
        }
        return totalCurrencyEntity;
    }

    @Override
    public TokenAmountEntity getExchangeAmount(String sourceToken, String targetToken, String sourceAmount) {
        TokenAmountEntity totalCurrencyEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                .sends(TokenAmountEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class)
                        .getExchangeAmount(Api.getExchangeAmount, sourceToken, targetToken, sourceAmount));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            totalCurrencyEntity=httpClientEntity.getObj();
        }else{
            totalCurrencyEntity=new TokenAmountEntity();
            totalCurrencyEntity.setCode(httpClientEntity.getCode());
            totalCurrencyEntity.setMsg(httpClientEntity.getMessage());
            totalCurrencyEntity.setRetcode(httpClientEntity.getCode());
        }
        return totalCurrencyEntity;
    }

    @Override
    public TokenListEntity getExchangeToken() {
        TokenListEntity totalCurrencyEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                .sends(TokenListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class)
                        .getExchangeToken(Api.getExchangeToken));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            totalCurrencyEntity=httpClientEntity.getObj();
        }else{
            totalCurrencyEntity=new TokenListEntity();
            totalCurrencyEntity.setCode(httpClientEntity.getCode());
            totalCurrencyEntity.setMsg(httpClientEntity.getMessage());
            totalCurrencyEntity.setRetcode(httpClientEntity.getCode());
        }
        return totalCurrencyEntity;
    }

    @Override
    public TokenExchangeResultEntity doExchangeToken(String currencyIdSource, String currencyIdTarget, double currencyAmountSource, String tradPass) {
        TokenExchangeResultEntity totalCurrencyEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                .sends(TokenExchangeResultEntity.class,"data", HttpClient.getInstance().retrofit().create(RetrofitService.class)
                        .doExchangeToken(Api.doExchangeToken, currencyIdSource, currencyIdTarget, currencyAmountSource, MD5Util.lock(tradPass)));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            totalCurrencyEntity=httpClientEntity.getObj();
        }else{
            totalCurrencyEntity=new TokenExchangeResultEntity();
            totalCurrencyEntity.setCode(httpClientEntity.getCode());
            totalCurrencyEntity.setMsg(httpClientEntity.getMessage());
            totalCurrencyEntity.setRetcode(httpClientEntity.getCode());
        }
        return totalCurrencyEntity;
    }
}
