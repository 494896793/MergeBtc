package com.bochat.app.common.model;

import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.SendSpeedEntity;
import com.bochat.app.model.bean.SpeedConverListEntity;
import com.bochat.app.model.bean.SpeedConverOrderDetailEntity;
import com.bochat.app.model.bean.SpeedConverOrderListEntity;
import com.bochat.app.model.bean.SpeedConverTradingEntity;

/**
 * 2019/5/4
 * Author LDL
 **/
public interface ISpeedConverModel {

    /*闪兑-闪兑大厅列表*/
    SpeedConverListEntity speedConverList(int startPage,String pageSize);

    /*闪兑-我的订单*/
    SpeedConverOrderListEntity mySpeedConver(int startPage, String pageSize, int type, int tradeStatus, String tradeTime);

    /*闪兑-发起闪兑*/
    SendSpeedEntity sendSpeedConver(int startId, int converId, double startNum, double converNum, int site, int isSync, String payPwd, long relevanceId);

    /*闪兑-我的订单-交易详情*/
    SpeedConverOrderDetailEntity myTradeDetail(int id,int type);

    /*闪兑兑换交易*/
    SpeedConverTradingEntity speedConverTrading(int orderId,String payPwd);

    /*闪兑取消交易*/
    CodeEntity cancelConverTrading(int orderId);


}
