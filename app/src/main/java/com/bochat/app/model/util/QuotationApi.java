package com.bochat.app.model.util;

import com.bochat.app.BuildConfig;
import com.bochat.app.app.view.DebugView;
import com.bochat.app.model.net.QuotationHttpClient;

/**
 * Created by cookie on 2017/5/22 15:13.
 *
 * @author LDL
 */
//All request API
public class QuotationApi implements DebugView.DebugValueProvider {

    public static String BASE_URL = "http://" + BuildConfig.QAPI_HOST + "/";
    public static String WS_SERVER_URI = "ws://" + BuildConfig.WAPI_HOST + "/websocket";

    /*获取交易区块*/
    public static final String getPationInfo = "app/marketInfo/getPationInfo";
    /*委托查询*/
    public static final String queryApplyList = "app/entrust/queryApplyList";
    /*交易币种选择*/
    public static final String queryByCurrency = "app/entrust/queryByCurrency";
    /*查询货币可用资产*/
    public static final String listResidueAmount = "app/entrust/listResidueAmount";
    /*买入卖出货币操作*/
    public static final String saleBuying = "app/entrust/saleBuying";
    /*是否收藏货币对*/
    public static final String isCollect = "app/entrust/isCollect";
    /* 查询市场交易规则 */
    public static final String queryTradingRules = "app/entrust/queryTradingRules";
    /* 查询当前用户是否收藏交易对 */
    public static final String queryCollection = "app/entrust/queryCollection";
    /*获取问题和答案*/
    public static final String queryKLineRecord = "/app/marketInfo/queryKLineRecord";
    /* 委托撤销 */
    public static final String revoke = "app/entrust/revoke";

    /*特权列表接口*/
    public static final String listPrivileges = "app/myprivilege/getMyPrivilege";

    private int debugType;

    public QuotationApi() {
    }

    public QuotationApi(int debugType) {
        this.debugType = debugType;
    }

    @Override
    public String getValue() {
        return debugType == 0 ? BASE_URL : WS_SERVER_URI;
    }

    @Override
    public void setValue(String value) {
        if (debugType == 0){
            BASE_URL = value;
        } else {
            WS_SERVER_URI = value;
        }
        QuotationHttpClient.release();
    }
}
