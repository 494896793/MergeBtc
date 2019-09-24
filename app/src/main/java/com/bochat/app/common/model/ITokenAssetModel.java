package com.bochat.app.common.model;

import com.bochat.app.model.bean.CandyDetailDataEntity;
import com.bochat.app.model.bean.ChangeDetailDataEntity;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.CurrencyDetailDataEntity;
import com.bochat.app.model.bean.CurrencyTradingDataEntity;
import com.bochat.app.model.bean.DataEntity;
import com.bochat.app.model.bean.GCcodeDataEntity;
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

/**
 * 2019/4/29
 * Author LDL
 **/
public interface ITokenAssetModel {

    /*资产-GC专用码*/
    GCcodeDataEntity getGCReceive();           //测试通过

    /*资产-账单交易记录(零钱罐、糖果盒)
    订单类型 0-全部；1-收入；2-支出；
            0-零钱罐，否则根据bid查询币交易记录*/
    TradingRecordDataEntity tradingRecord(int start, int offset, int bid, int orderType);       //测试通过

    /*资产-零钱罐交易记录详情*/
    ChangeDetailDataEntity getChangeDetails(int id);        //测试通过

    /*资产-账单收入支出总额*/
    IncomeDataEntity getIncomeSpending(int bid);                //测试通过

    /*资产-糖果盒交易记录详情*/
    CandyDetailDataEntity getCandyDetails(int id);          //测试通过

    /*资产-资产转出*/
    CodeEntity addTrunOut(double trunoutnum,String walletaddre,String tradpassw,int bid);          //测试通过

    /*资产-GC划转*/
    CodeEntity addGCTransfer(int number, String password, int recipientUserId);         //测试通过

    /*资产-查询币总额*/
    TotalCurrencyEntity getUserCurrencyCny();      //测试通过

    /*资产-资产转出提示信息*/
    OutPromptDataEntity getOutPrompt(int bid);                  //测试通过

    /*资产-查询所有币种列表*/
    UserCurrencyDataEntity listUserCurrency();        //测试通过

    /*资产-查询所有币种列表(选择令牌、接收选择资产)*/
    DataEntity listPlatformCurrency(String userId);             //测试通过

    /*资产-查询币详情、接收*/
    CurrencyDetailDataEntity getUserCurrencyDetails(int bid);           //测试通过

    /*资产-查询币划转、发送交易记录*/
    CurrencyTradingDataEntity getUserCurrencyTrading(int start, int offset, int bid);               //测试通过

    IncomeOfTodayEntity getIncomeOfToday(int currencyId);
    
    TokenRateEntity getTokenRate(String sourceToken, String targetToken);
    
    TokenAmountEntity getExchangeAmount(String sourceToken, String targetToken, String sourceAmount);
    
    TokenListEntity getExchangeToken();
    
    TokenExchangeResultEntity doExchangeToken(String currencyIdSource, String currencyIdTarget, double currencyAmountSource, String tradPass);
}
