package com.bochat.app.model;

import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IGroupLocalModel;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.ILoginModel;
import com.bochat.app.common.model.IMarketCenterModel;
import com.bochat.app.common.model.IMarketQuotationDetailModel;
import com.bochat.app.common.model.IMarketQuotationEntrustModel;
import com.bochat.app.common.model.IMoneyModel;
import com.bochat.app.common.model.IOSSModel;
import com.bochat.app.common.model.IRecommendFriendModel;
import com.bochat.app.common.model.IRecommendGroupModel;
import com.bochat.app.common.model.IRedPacketModel;
import com.bochat.app.common.model.ISendMessageModel;
import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.common.model.ISpeedConverModel;
import com.bochat.app.common.model.IThirdServicesModel;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.model.IUpgradeModel;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.model.modelImpl.DynamicModel;
import com.bochat.app.model.modelImpl.GroupModule;
import com.bochat.app.model.modelImpl.IMModel;
import com.bochat.app.model.modelImpl.LoginModel;
import com.bochat.app.model.modelImpl.MarketCenter.MarketCenterModel;
import com.bochat.app.model.modelImpl.MarketQuotationDetailModel;
import com.bochat.app.model.modelImpl.MarketQuotationEntrustModel;
import com.bochat.app.model.modelImpl.MoneyModel;
import com.bochat.app.model.modelImpl.OSSModel;
import com.bochat.app.model.modelImpl.RecommendFriendModel;
import com.bochat.app.model.modelImpl.RecommendGroupModel;
import com.bochat.app.model.modelImpl.RedPacketModel;
import com.bochat.app.model.modelImpl.SendMessageModel;
import com.bochat.app.model.modelImpl.SettingModule;
import com.bochat.app.model.modelImpl.SpeedConverModel;
import com.bochat.app.model.modelImpl.ThirdServicesModel;
import com.bochat.app.model.modelImpl.TokenAssetModel;
import com.bochat.app.model.modelImpl.UpgradeModel;
import com.bochat.app.model.modelImpl.UserModule;
import com.bochat.app.mvp.injector.scope.BusinessScope;

import dagger.Module;
import dagger.Provides;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 09:50
 * Description :
 */
@Module
public class ModelModule {
//    @Provides
//    @BusinessScope
//    public IUserModel provideUserModel() {
//        return new UserModule();
//    }

    @Provides
    @BusinessScope
    public IUserModel proivideUserModule() {
        return new UserModule();
    }

    @Provides
    @BusinessScope
    public ILoginModel provideLoginModel() {
        return new LoginModel();
    }

    @Provides
    @BusinessScope
    public ISendMessageModel provideSendMessageModel() {
        return new SendMessageModel();
    }

    @Provides
    @BusinessScope
    public IGroupModule provideGroupModule() {
        return new GroupModule();
    }

    @Provides
    @BusinessScope
    public IIMModel provideIMModule() {
        return new IMModel();
    }

    @Provides
    @BusinessScope
    public IOSSModel provideIOSSModel() {
        return new OSSModel();
    }

    @Provides
    @BusinessScope
    public IUserLocalModel provideIUserModule() {
        return new UserModule();
    }

    @Provides
    @BusinessScope
    public IGroupLocalModel provideIGroupLocalModel() {
        return new GroupModule();
    }

    @Provides
    @BusinessScope
    public ITokenAssetModel provideITokenAssetModel() {
        return new TokenAssetModel();
    }

    @Provides
    @BusinessScope
    public IMoneyModel provideIMoneyModel() {
        return new MoneyModel();
    }

    @Provides
    @BusinessScope
    public ISpeedConverModel provideISpeedConverModel() {
        return new SpeedConverModel();
    }

    @Provides
    @BusinessScope
    public IRedPacketModel provideIRedPacketModel() {
        return new RedPacketModel();
    }

    @Provides
    @BusinessScope
    public IDynamicModel provideIDynamicModel() {
        return new DynamicModel();
    }

    @Provides
    @BusinessScope
    public ISettingModule provideISettingModule() {
        return new SettingModule();
    }

    @Provides
    @BusinessScope
    public IUpgradeModel provideIUpgradeModel() {
        return new UpgradeModel();
    }

    @Provides
    @BusinessScope
    public IThirdServicesModel provideIThirdServicesModel() {
        return new ThirdServicesModel();
    }

    @Provides
    @BusinessScope
    public IRecommendFriendModel provideIRecommendFriendModel() {
        return new RecommendFriendModel();
    }

    @Provides
    @BusinessScope
    public IRecommendGroupModel provideIRecommendGroupModel() {
        return new RecommendGroupModel();
    }

    @Provides
    @BusinessScope
    public IMarketCenterModel provideIMarketCenterModel() {
        return MarketCenterModel.getInstance();
    }

    @Provides
    @BusinessScope
    public IMarketQuotationEntrustModel provideMarketQuotationEntrustModel() {
        return new MarketQuotationEntrustModel();
    }

    @Provides
    @BusinessScope
    public IMarketQuotationDetailModel provideIMarketQuotationDetailModel() {
        return new MarketQuotationDetailModel();
    }
}