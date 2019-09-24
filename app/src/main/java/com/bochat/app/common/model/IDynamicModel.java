package com.bochat.app.common.model;

import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.DynamicBannerListEntity;
import com.bochat.app.model.bean.DynamicFlashAdressEntity;
import com.bochat.app.model.bean.DynamicFlashListEntity;
import com.bochat.app.model.bean.DynamicIncomeOfTodayEntity;
import com.bochat.app.model.bean.DynamicNoticeListEntity;
import com.bochat.app.model.bean.DynamicPlushEntity;
import com.bochat.app.model.bean.DynamicQueryUserBXInfoEntity;
import com.bochat.app.model.bean.DynamicShopGameListEntity;
import com.bochat.app.model.bean.DynamicShopTypeListEntity;
import com.bochat.app.model.bean.DynamicTopShopListEntity;
import com.bochat.app.model.bean.PationIntoListEntity;
import com.bochat.app.model.bean.PrivilegeListEntity;
import com.bochat.app.model.bean.ProjectIdentificationEntity;
import com.bochat.app.model.bean.ProtocolBookEntity;
import com.bochat.app.model.bean.RechargeVipEntity;
import com.bochat.app.model.bean.RechargeVipSuccessEntity;
import com.bochat.app.model.bean.RedHallDetailEntity;
import com.bochat.app.model.bean.RedHallListEntity;
import com.bochat.app.model.bean.ResidueAmountListEntity;
import com.bochat.app.model.bean.ShakyListEntity;
import com.bochat.app.model.bean.TradingRulesEntity;
import com.bochat.app.model.bean.ViewSpotEntity;
import com.bochat.app.model.bean.VipStatuEntity;
import com.bochat.app.model.modelImpl.MarketCenter.KLineListEntity;

import java.math.BigInteger;

/**
 * 2019/5/5
 * Author LDL
 **/
public interface IDynamicModel {

    /*查询应用商店前四条-首页*/
    DynamicTopShopListEntity listApplicationHome(String userId);

    /*查询应用商店类型*/
    DynamicShopTypeListEntity getAppStoreType(String userId);

    /*查询应用商店、推荐游戏列表
    type:   2 - 应用商店,  3 - 推荐游戏*/
    DynamicShopGameListEntity listApplication(int start, int offset, int type, int typeId);

    DynamicShopGameListEntity listApplication(int type, int isFeatured);

    /*查询应用*/
    DynamicShopGameListEntity searchApplication(int type, String name);

    /*查询banner列表
    type:1：理财通;2：推荐游戏;3:商城；4：动态*/
    DynamicBannerListEntity listBanner(int type);

    /*查询公告列表
    type:    1-动态*/
    DynamicNoticeListEntity listAnnouncement(int start, int offset, int type);

    //    获取今日看点列表
    ViewSpotEntity getInfomationList(int start, int offset, String keyword);

    /*添加项目方合作申请*/
    CodeEntity addProjectParty(String companyName, String website, String logo, String license);

    /*项目方认证状态*/
    ProjectIdentificationEntity getProjectParty();

    /*BoChat项目合作协议书*/
    ProtocolBookEntity getProtocolBook();

    /*查询第三方服务列表*/
    DynamicTopShopListEntity listAppService();

    /*活动中心居民招募查询金币状态*/
    DynamicQueryUserBXInfoEntity queryUserBXInfo();
    /*活动中心获取每日收益*/

    DynamicIncomeOfTodayEntity incomeOfToday();

    /*查询活动中心*/

    ShakyListEntity listActivities(String start, String offset);

    /*获取交易区块*/
    PationIntoListEntity getPationInfo();

    DynamicBannerListEntity getBannerForYysc();

    DynamicShopGameListEntity searchListApplication(String type, String classification, String name, int isHottest, int id, int isFeatured);

    ResidueAmountListEntity listResidueAmount(long userId, String currencyIdL, String currencyIdR);

    /*查询快讯列表*/
    DynamicFlashListEntity messageLists(String page, String pageSize);

    /*发送快讯点赞列表*/
    CodeEntity like(String flashId, String option);

    /*快讯详情页面*/
    DynamicPlushEntity push();

    TradingRulesEntity queryTradingRules(long marketId);

    KLineListEntity queryKLineRecord(long marketId, int period, long startId, int offset);

    CodeEntity saleBuying(long marketId, String price, double num, int type, long currencyIdL, long currencyIdR);

    /*快讯二维码地址*/
    DynamicFlashAdressEntity findDownloadPath();

    PrivilegeListEntity listPrivileges(int currentPage, int pageSize, boolean isNoData);
    /*获取是否开通Vip*/
    VipStatuEntity getOpenVip();
    /*获取红包列表*/
    RedHallListEntity getRewardHallList(String startPage,String pageSize);
    /*获取领取详情*/
    RedHallDetailEntity getRewardReceiveDetails(String startPage,String pageSize,String rewardId);
    /*查询Vip价格列表*/
    RechargeVipEntity query();
    /*Vip购买*/
    RechargeVipSuccessEntity payVip(String id, String tradePass);

}