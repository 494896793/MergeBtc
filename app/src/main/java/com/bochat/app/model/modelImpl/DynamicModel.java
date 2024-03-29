package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.IDynamicModel;
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
import com.bochat.app.model.bean.HttpClientEntity;
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
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.modelImpl.MarketCenter.KLineListEntity;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.QuotationHttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;
import com.bochat.app.model.util.QuotationApi;

/**
 * 2019/5/5
 * Author LDL
 **/
public class DynamicModel implements IDynamicModel {

    public DynamicTopShopListEntity listApplicationHome(String userId) {
        DynamicTopShopListEntity dynamicTopShopListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicTopShopListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).listApplicationHome(Api.listApplicationHome, userId));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            dynamicTopShopListEntity = httpClientEntity.getObj();
        } else {
            dynamicTopShopListEntity = new DynamicTopShopListEntity();
            dynamicTopShopListEntity.setCode(httpClientEntity.getCode());
            dynamicTopShopListEntity.setMsg(httpClientEntity.getMessage());
            dynamicTopShopListEntity.setRetcode(httpClientEntity.getCode());
        }
        return dynamicTopShopListEntity;
    }

    @Override
    public DynamicShopTypeListEntity getAppStoreType(String userId) {
        DynamicShopTypeListEntity dynamicTopShopListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicShopTypeListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).getAppStoreType(Api.getAppStoreType, userId));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            dynamicTopShopListEntity = httpClientEntity.getObj();
        } else {
            dynamicTopShopListEntity = new DynamicShopTypeListEntity();
            dynamicTopShopListEntity.setCode(httpClientEntity.getCode());
            dynamicTopShopListEntity.setMsg(httpClientEntity.getMessage());
            dynamicTopShopListEntity.setRetcode(httpClientEntity.getCode());
        }
        return dynamicTopShopListEntity;
    }

    @Override
    public DynamicShopGameListEntity listApplication(int start, int offset, int type, int typeId) {
        DynamicShopGameListEntity dynamicShopGameListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicShopGameListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).listApplication(Api.listApplication, start, offset, type, typeId));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            dynamicShopGameListEntity = httpClientEntity.getObj();
        } else {
            dynamicShopGameListEntity = new DynamicShopGameListEntity();
            dynamicShopGameListEntity.setCode(httpClientEntity.getCode());
            dynamicShopGameListEntity.setMsg(httpClientEntity.getMessage());
            dynamicShopGameListEntity.setRetcode(httpClientEntity.getCode());
        }
        return dynamicShopGameListEntity;
    }

    @Override
    public DynamicShopGameListEntity listApplication(int type, int isFeatured) {
        DynamicShopGameListEntity dynamicShopGameListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicShopGameListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).listApplication(Api.listApplication, type, isFeatured));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            dynamicShopGameListEntity = httpClientEntity.getObj();
        } else {
            dynamicShopGameListEntity = new DynamicShopGameListEntity();
            dynamicShopGameListEntity.setCode(httpClientEntity.getCode());
            dynamicShopGameListEntity.setMsg(httpClientEntity.getMessage());
            dynamicShopGameListEntity.setRetcode(httpClientEntity.getCode());
        }
        return dynamicShopGameListEntity;
    }

    @Override
    public DynamicShopGameListEntity searchApplication(int type, String name) {
        DynamicShopGameListEntity dynamicShopGameListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicShopGameListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).searchListApplication(Api.listApplication, type + "", name));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            dynamicShopGameListEntity = httpClientEntity.getObj();
        } else {
            dynamicShopGameListEntity = new DynamicShopGameListEntity();
            dynamicShopGameListEntity.setCode(httpClientEntity.getCode());
            dynamicShopGameListEntity.setMsg(httpClientEntity.getMessage());
            dynamicShopGameListEntity.setRetcode(httpClientEntity.getCode());
        }
        return dynamicShopGameListEntity;
    }

    @Override
    public DynamicShopGameListEntity searchListApplication(String type, String classification, String name, int isHottest, int id, int isFeatured) {
        DynamicShopGameListEntity dynamicShopGameListEntity = null;
        HttpClientEntity httpClientEntity = null;
        if (isHottest == -1 && id == -1 && isFeatured == -1) {
            httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicShopGameListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).searchListApplication(Api.listApplication, type));
        } else if (isHottest != -1 && id == -1 && isFeatured == -1) {
            httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicShopGameListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).searchListApplication(Api.listApplication, type, isHottest));
        } else {
            httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicShopGameListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).searchListApplication(Api.listApplication, type, classification, name, isHottest, id, isFeatured));
        }
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            dynamicShopGameListEntity = httpClientEntity.getObj();
        } else {
            dynamicShopGameListEntity = new DynamicShopGameListEntity();
            dynamicShopGameListEntity.setCode(httpClientEntity.getCode());
            dynamicShopGameListEntity.setMsg(httpClientEntity.getMessage());
            dynamicShopGameListEntity.setRetcode(httpClientEntity.getCode());
        }
        return dynamicShopGameListEntity;
    }

    @Override
    public DynamicFlashListEntity messageLists(String page, String pageSize) {
        DynamicFlashListEntity dynamicFlashListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicFlashListEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).messageLists(Api.messageLists, page, pageSize));
//        LogUtil.LogDebug("ggyy","ggyy =="+httpClientEntity.getObj());
//        LogUtil.LogDebug("ggyy","ggyy =="+httpClientEntity.getJsonData());
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            dynamicFlashListEntity = httpClientEntity.getObj();
        } else {
            dynamicFlashListEntity = new DynamicFlashListEntity();
            dynamicFlashListEntity.setCode(httpClientEntity.getCode());
            dynamicFlashListEntity.setMsg(httpClientEntity.getMessage());
            dynamicFlashListEntity.setRetcode(httpClientEntity.getCode());
        }
//        LogUtil.LogDebug("ggyy","ggyy =="+dynamicFlashListEntity.getItems());

        return dynamicFlashListEntity;

    }

    @Override
    public CodeEntity like(String flashId, String option) {
        CodeEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).like(Api.like, flashId, option));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new CodeEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;

    }

    @Override
    public DynamicPlushEntity push() {
        DynamicPlushEntity dynamicFlashEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicPlushEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).push(Api.push));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            dynamicFlashEntity = httpClientEntity.getObj();
        } else {
            dynamicFlashEntity = new DynamicPlushEntity();
            dynamicFlashEntity.setCode(httpClientEntity.getCode());
            dynamicFlashEntity.setMsg(httpClientEntity.getMessage());
            dynamicFlashEntity.setRetcode(httpClientEntity.getCode());
        }
        return dynamicFlashEntity;
    }


    @Override
    public ResidueAmountListEntity listResidueAmount(long userId, String currencyIdL, String currencyIdR) {
        ResidueAmountListEntity residueAmountListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(ResidueAmountListEntity.class, QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).listResidueAmount(QuotationApi.listResidueAmount, userId, currencyIdL, currencyIdR));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            residueAmountListEntity = httpClientEntity.getObj();
        } else {
            residueAmountListEntity = new ResidueAmountListEntity();
            residueAmountListEntity.setCode(httpClientEntity.getCode());
            residueAmountListEntity.setMsg(httpClientEntity.getMessage());
            residueAmountListEntity.setRetcode(httpClientEntity.getCode());
        }
        return residueAmountListEntity;
    }

    @Override
    public TradingRulesEntity queryTradingRules(long marketId) {
        TradingRulesEntity tradingRulesEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(TradingRulesEntity.class, "data", QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).queryTradingRules(QuotationApi.queryTradingRules, marketId));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            tradingRulesEntity = httpClientEntity.getObj();
        } else {
            tradingRulesEntity = new TradingRulesEntity();
            tradingRulesEntity.setCode(httpClientEntity.getCode());
            tradingRulesEntity.setMsg(httpClientEntity.getMessage());
            tradingRulesEntity.setRetcode(httpClientEntity.getCode());
        }
        return tradingRulesEntity;
    }

    @Override
    public CodeEntity saleBuying(long marketId, String price, double num, int type, long currencyIdL, long currencyIdR) {
        CodeEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(CodeEntity.class, QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).saleBuying(QuotationApi.saleBuying, marketId, price, num, type, currencyIdL, currencyIdR));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new CodeEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }

        return entity;
    }

    @Override
    public DynamicFlashAdressEntity findDownloadPath() {
        DynamicFlashAdressEntity flashAdressEntity;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicFlashAdressEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).findDownloadPath(Api.findDownloadPath));

        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            flashAdressEntity = httpClientEntity.getObj();
        } else {
            flashAdressEntity = new DynamicFlashAdressEntity();
            flashAdressEntity.setCode(httpClientEntity.getCode());
            flashAdressEntity.setMsg(httpClientEntity.getMessage());
            flashAdressEntity.setRetcode(httpClientEntity.getCode());
        }

        return flashAdressEntity;
    }

    @Override
    public PrivilegeListEntity listPrivileges(int currentPage, int pageSize, boolean isNoData) {

        PrivilegeListEntity entity = null;
//        if (BuildConfig.IS_DEBUG) {
//            Gson gson = new Gson();
//            String json = isNoData ? "{\"msg\":\"成功\",\"items\":[],\"retcode\":0,\"timestamp\":1560320417454}" : "{\"msg\":\"成功\",\"items\":[{\"id\":12,\"name\":\"BoChat创世居民\",\"valid_time\":\"永久有效\",\"state\":0,\"type\":1,\"describes\":1,\"url\":\"\"},{\"id\":13,\"name\":\"红包大厅VIP\",\"valid_time\":\"2019/08/16-2019/10/24\",\"state\":0,\"type\":2,\"describes\":0,\"url\":\"\"},{\"id\":14,\"name\":\"红包大厅VIP\",\"valid_time\":\"2019/09/16-2020/10/11\",\"state\":1,\"type\":2,\"describes\":0,\"url\":\"\"}],\"retcode\":0,\"timestamp\":1560320417454}";
//            entity = gson.fromJson(json, PrivilegeListEntity.class);
//        } else {
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(PrivilegeListEntity.class, "data", QuotationHttpClient.getInstance().retrofit()
                .create(RetrofitService.class).listPrivileges(QuotationApi.listPrivileges, currentPage, pageSize));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new PrivilegeListEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
//        }
        return entity;
    }

    @Override
    public VipStatuEntity getOpenVip() {
        VipStatuEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(VipStatuEntity.class, "data", QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).getOpenVip(Api.getOpenVip));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new VipStatuEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public RedHallListEntity getRewardHallList(String startPage,String pageSize) {
        RedHallListEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(RedHallListEntity.class, "data", QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).getRewardHallList(Api.getRewardHallList,startPage,pageSize ));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new RedHallListEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public RedHallDetailEntity getRewardReceiveDetails(String startPage, String pageSize, String rewardId) {
        RedHallDetailEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(RedHallDetailEntity.class, "data", QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).getRewardReceiveDetails(Api.getRewardReceiveDetails,startPage,pageSize,rewardId ));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new RedHallDetailEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public RechargeVipEntity query() {
        RechargeVipEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(RechargeVipEntity.class, "data", QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).query(Api.query));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new RechargeVipEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public RechargeVipSuccessEntity payVip(String id, String tradePass) {
        RechargeVipSuccessEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(RechargeVipSuccessEntity.class, "data", QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).payVip(Api.payVip,id,tradePass ));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new RechargeVipSuccessEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public KLineListEntity queryKLineRecord(long marketId, int period, long startId, int offset) {
        KLineListEntity kLineListEntity;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance()
                .sends(KLineListEntity.class, "data", QuotationHttpClient.getInstance().retrofit()
                        .create(RetrofitService.class)
                        .queryKLineRecord(QuotationApi.queryKLineRecord, marketId, period, startId, offset));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            kLineListEntity = httpClientEntity.getObj();
        } else {
            kLineListEntity = new KLineListEntity();
            kLineListEntity.setCode(httpClientEntity.getCode());
            kLineListEntity.setMsg(httpClientEntity.getMessage());
            kLineListEntity.setRetcode(httpClientEntity.getCode());
        }
        return kLineListEntity;
    }

    @Override
    public DynamicBannerListEntity listBanner(int type) {
        DynamicBannerListEntity dynamicBannerListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicBannerListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).listBanner(Api.listBanner, type));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            dynamicBannerListEntity = httpClientEntity.getObj();
        } else {
            dynamicBannerListEntity = new DynamicBannerListEntity();
            dynamicBannerListEntity.setCode(httpClientEntity.getCode());
            dynamicBannerListEntity.setMsg(httpClientEntity.getMessage());
            dynamicBannerListEntity.setRetcode(httpClientEntity.getCode());
        }
        return dynamicBannerListEntity;
    }

    @Override
    public DynamicNoticeListEntity listAnnouncement(int start, int offset, int type) {
        DynamicNoticeListEntity dynamicNoticeListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicNoticeListEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).listAnnouncement(Api.listAnnouncement, start, offset, type));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            dynamicNoticeListEntity = httpClientEntity.getObj();
        } else {
            dynamicNoticeListEntity = new DynamicNoticeListEntity();
            dynamicNoticeListEntity.setCode(httpClientEntity.getCode());
            dynamicNoticeListEntity.setMsg(httpClientEntity.getMessage());
            dynamicNoticeListEntity.setRetcode(httpClientEntity.getCode());
        }
        return dynamicNoticeListEntity;
    }

    @Override
    public ViewSpotEntity getInfomationList(int start, int offset, String keyword) {
        ViewSpotEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(ViewSpotEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).getInfomationList(Api.getInfomationList, start, offset, keyword));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new ViewSpotEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public CodeEntity addProjectParty(String companyName, String website, String logo, String license) {
        CodeEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).addProjectParty(Api.addProjectParty, companyName, website, logo, license));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new CodeEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public ProjectIdentificationEntity getProjectParty() {
        ProjectIdentificationEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(ProjectIdentificationEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).getProjectParty(Api.getProjectParty, ""));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new ProjectIdentificationEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public ProtocolBookEntity getProtocolBook() {
        ProtocolBookEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(ProtocolBookEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).getProtocolBook(Api.getProtocolBook, ""));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new ProtocolBookEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public DynamicTopShopListEntity listAppService() {
        DynamicTopShopListEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicTopShopListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).listAppService(Api.listAppService));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new DynamicTopShopListEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public DynamicQueryUserBXInfoEntity queryUserBXInfo() {
        DynamicQueryUserBXInfoEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicQueryUserBXInfoEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).queryUserBXInfo(Api.queryUserBXInfo));

        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new DynamicQueryUserBXInfoEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public DynamicIncomeOfTodayEntity incomeOfToday() {
        DynamicIncomeOfTodayEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicIncomeOfTodayEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).incomeOfToday(Api.incomeOfToday, "4"));

        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new DynamicIncomeOfTodayEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }


    @Override
    public ShakyListEntity listActivities(String start, String offset) {
        ShakyListEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance()
                .sends(ShakyListEntity.class, "data", HttpClient.getInstance().retrofit()
                        .create(RetrofitService.class).listActivities(Api.listActivitys, start, offset));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new ShakyListEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public PationIntoListEntity getPationInfo() {
        PationIntoListEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(PationIntoListEntity.class, "data", QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).getPationInfo(QuotationApi.getPationInfo));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new PationIntoListEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public DynamicBannerListEntity getBannerForYysc() {
        DynamicBannerListEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(DynamicBannerListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).getBannerForYysc(Api.getBannerForYysc));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new DynamicBannerListEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

}
