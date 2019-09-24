package com.bochat.app.app;

import android.content.Context;

import com.bochat.app.app.activity.AddFriendActivity;
import com.bochat.app.app.activity.AddFriendEditRequestActivity;
import com.bochat.app.app.activity.ApplyListActivity;
import com.bochat.app.app.activity.BidListActivity;
import com.bochat.app.app.activity.BoChatActivity;
import com.bochat.app.app.activity.ConversationDetailActivity;
import com.bochat.app.app.activity.DealAddFriendActivity;
import com.bochat.app.app.activity.DynamicNoticeActivity;
import com.bochat.app.app.activity.DynamicNoticeDetailActivity;
import com.bochat.app.app.activity.FriendDetailActivity;
import com.bochat.app.app.activity.FriendManageActivity;
import com.bochat.app.app.activity.FriendManageNoteActivity;
import com.bochat.app.app.activity.GroupAddManagerActivity;
import com.bochat.app.app.activity.GroupDetailActivity;
import com.bochat.app.app.activity.GroupManageActivity;
import com.bochat.app.app.activity.GroupManageEditActivity;
import com.bochat.app.app.activity.GroupManageEditNameActivity;
import com.bochat.app.app.activity.GroupManageForbiddenActivity;
import com.bochat.app.app.activity.GroupManageJoinFilterActivity;
import com.bochat.app.app.activity.GroupManageManagerActivity;
import com.bochat.app.app.activity.GroupManageUpgradeActivity;
import com.bochat.app.app.activity.GroupManagerSettingActivity;
import com.bochat.app.app.activity.GroupMemberActivity;
import com.bochat.app.app.activity.GroupMemberManageActivity;
import com.bochat.app.app.activity.GroupMemberManageNoteActivity;
import com.bochat.app.app.activity.GroupMentionedSeleteActivity;
import com.bochat.app.app.activity.GroupQuestionAnswerActivity;
import com.bochat.app.app.activity.HeaderDetailActivity;
import com.bochat.app.app.activity.ListAppActivity;
import com.bochat.app.app.activity.LoginActivity;
import com.bochat.app.app.activity.QRCardActivity;
import com.bochat.app.app.activity.RedHallDetailActivity;
import com.bochat.app.app.activity.dynamic.RechargeVipSuccessActivity;
import com.bochat.app.app.activity.RedPacketDetailActivity;
import com.bochat.app.app.activity.RegisterActivity;
import com.bochat.app.app.activity.SearchAddressBookActivity;
import com.bochat.app.app.activity.SearchAppActivity;
import com.bochat.app.app.activity.SearchConversationActivity;
import com.bochat.app.app.activity.SearchFriendActivity;
import com.bochat.app.app.activity.SearchGroupActivity;
import com.bochat.app.app.activity.SearchMessageActivity;
import com.bochat.app.app.activity.SelectFriendActivity;
import com.bochat.app.app.activity.SendRedPacketActivity;
import com.bochat.app.app.activity.SplashScreenActivity;
import com.bochat.app.app.activity.TestIncomeOfTodayActivity;
import com.bochat.app.app.activity.WebActivity;
import com.bochat.app.app.activity.addressbook.AddressPublicActivity;
import com.bochat.app.app.activity.bill.GCSpecialCodeActivity;
import com.bochat.app.app.activity.bill.OrderListActivity;
import com.bochat.app.app.activity.bill.PropertyCashOutActivity;
import com.bochat.app.app.activity.bill.PropertyRechargeActivity;
import com.bochat.app.app.activity.bill.PropertyRechargeResultActivity;
import com.bochat.app.app.activity.bill.QuickExchangeActivity;
import com.bochat.app.app.activity.bill.QuickExchangeDetailActivity;
import com.bochat.app.app.activity.bill.QuickExchangeHallActivity;
import com.bochat.app.app.activity.bill.TokenDetailActivity;
import com.bochat.app.app.activity.bill.TokenSelectActivity;
import com.bochat.app.app.activity.bill.TokenTransferActivity;
import com.bochat.app.app.activity.bill.TokenTransferReceiveActivity;
import com.bochat.app.app.activity.bill.TokenTransferSelectCoinActivity;
import com.bochat.app.app.activity.bill.WalletActivity;
import com.bochat.app.app.activity.bill.WalletActivity_GY;
import com.bochat.app.app.activity.dynamic.DynamicFlashDetialActivity;
import com.bochat.app.app.activity.dynamic.DynamicListAppDetailActivity;
import com.bochat.app.app.activity.dynamic.DynamicNoticeFlashActivity;
import com.bochat.app.app.activity.dynamic.DynamicRecruitActivity;
import com.bochat.app.app.activity.dynamic.FastSpeedActivity;
import com.bochat.app.app.activity.dynamic.MarketQuotationActivity;
import com.bochat.app.app.activity.dynamic.MarketQuotationDetailActivity;
import com.bochat.app.app.activity.dynamic.PrivilegeActivity;
import com.bochat.app.app.activity.dynamic.ProjectIdentificatiionActivity;
import com.bochat.app.app.activity.dynamic.ProjectIdentificationResultActivity;
import com.bochat.app.app.activity.dynamic.ProtocolBookActivity;
import com.bochat.app.app.activity.dynamic.RechargeVipActivity;
import com.bochat.app.app.activity.dynamic.RedHallActivity;
import com.bochat.app.app.activity.dynamic.ShakyCenterActivity;
import com.bochat.app.app.activity.dynamic.ViewLookSearchActivity;
import com.bochat.app.app.activity.dynamic.ViewSpotActivity;
import com.bochat.app.app.activity.mine.AboutUsActivity;
import com.bochat.app.app.activity.mine.BankCardActivity;
import com.bochat.app.app.activity.mine.BankCardAddActivity;
import com.bochat.app.app.activity.mine.BillActivity;
import com.bochat.app.app.activity.mine.BillDetailActivity;
import com.bochat.app.app.activity.mine.BlackListActivity;
import com.bochat.app.app.activity.mine.ChangeNotificationActivity;
import com.bochat.app.app.activity.mine.EditPasswordActivity;
import com.bochat.app.app.activity.mine.EditPayPasswordActivity;
import com.bochat.app.app.activity.mine.EditUserInfoActivity;
import com.bochat.app.app.activity.mine.GetVerifyCodeActivity;
import com.bochat.app.app.activity.mine.InvitationActivity;
import com.bochat.app.app.activity.mine.PhoneInfoActivity;
import com.bochat.app.app.activity.mine.RealNameAuthActivity;
import com.bochat.app.app.activity.mine.RealNameInfoActivity;
import com.bochat.app.app.activity.mine.SelectBankActivity;
import com.bochat.app.app.activity.mine.SetPasswordActivity;
import com.bochat.app.app.activity.mine.SetPayPasswordActivity;
import com.bochat.app.app.activity.mine.SettingsActivity;
import com.bochat.app.business.BusinessModule;
import com.bochat.app.model.ModelModuleForTest;
import com.bochat.app.mvp.injector.ContextLife;
import com.bochat.app.mvp.injector.ContextLifeConst;
import com.bochat.app.mvp.injector.component.ApplicationComponent;
import com.bochat.app.mvp.injector.module.ActivityModule;
import com.bochat.app.mvp.injector.scope.ActivityScope;
import com.bochat.app.mvp.view.BaseActivity;

import dagger.Component;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 09:50
 * Description :
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, BusinessModule.class, ModelModuleForTest.class})
public interface ActivityComponent {

    BaseActivity getActivity();

    @ContextLife(ContextLifeConst.CONTEXT_ACTIVITY)
    Context getActivityContext();

    @ContextLife(ContextLifeConst.CONTEXT_APPLICATION)
    Context getApplicationContext();

    void inject(LoginActivity activity);

    void inject(BoChatActivity activity);

    void inject(RegisterActivity activity);

    void inject(SplashScreenActivity activity);

    void inject(AddFriendActivity activity);

    void inject(SearchFriendActivity activity);

    void inject(FriendDetailActivity activity);

    void inject(AddFriendEditRequestActivity activity);

    void inject(SelectFriendActivity activity);

    void inject(DealAddFriendActivity activity);

    void inject(ApplyListActivity activity);

    void inject(SearchConversationActivity activity);

    void inject(AboutUsActivity activity);

    void inject(BankCardAddActivity activity);

    void inject(BankCardActivity activity);

    void inject(BillActivity activity);

    void inject(BillDetailActivity activity);

    void inject(BlackListActivity activity);

    void inject(ChangeNotificationActivity activity);

    void inject(EditPasswordActivity activity);

    void inject(EditUserInfoActivity activity);

    void inject(InvitationActivity activity);

    void inject(RealNameAuthActivity activity);

    void inject(RealNameInfoActivity activity);

    void inject(SelectBankActivity activity);

    void inject(SetPasswordActivity activity);

    void inject(SettingsActivity activity);

    void inject(GroupDetailActivity activity);

    void inject(GroupManageActivity activity);

    void inject(GroupManageEditActivity activity);

    void inject(GroupManageEditNameActivity activity);

    void inject(GroupManageForbiddenActivity activity);

    void inject(GroupManageUpgradeActivity activity);

    void inject(GroupMemberActivity activity);

    void inject(GroupMemberManageActivity activity);

    void inject(GroupMemberManageNoteActivity activity);

    void inject(ConversationDetailActivity activity);

    void inject(FriendManageActivity activity);

    void inject(FriendManageNoteActivity activity);

    void inject(SearchMessageActivity activity);

    void inject(QRCardActivity activity);

    void inject(WalletActivity activity);

    void inject(PropertyRechargeActivity activity);

    void inject(PropertyCashOutActivity activity);

    void inject(GCSpecialCodeActivity activity);

    void inject(TokenTransferReceiveActivity activity);

    void inject(TokenTransferSelectCoinActivity activity);

    void inject(TokenTransferActivity activity);

    void inject(QuickExchangeActivity activity);

    void inject(TokenDetailActivity activity);

    void inject(OrderListActivity activity);

    void inject(QuickExchangeDetailActivity activity);

    void inject(QuickExchangeHallActivity activity);

    void inject(PropertyRechargeResultActivity activity);

    void inject(WebActivity activity);

    void inject(ListAppActivity activity);

    void inject(DynamicNoticeActivity activity);

    void inject(SendRedPacketActivity activity);

    void inject(BidListActivity activity);

    void inject(RedPacketDetailActivity activity);

    void inject(SearchAddressBookActivity activity);

    void inject(GetVerifyCodeActivity activity);

    void inject(PhoneInfoActivity activity);

    void inject(EditPayPasswordActivity activity);

    void inject(SetPayPasswordActivity activity);

    void inject(DynamicNoticeDetailActivity activity);

    void inject(SearchGroupActivity activity);

    void inject(GroupManageManagerActivity activity);

    void inject(GroupManageJoinFilterActivity activity);

    void inject(ViewSpotActivity activity);

    void inject(ProjectIdentificatiionActivity activity);

    void inject(ProjectIdentificationResultActivity activity);

    void inject(ProtocolBookActivity activity);

    void inject(ViewLookSearchActivity activity);

    void inject(TestIncomeOfTodayActivity activity);

    void inject(FastSpeedActivity activity);

    void inject(TokenSelectActivity activity);

    void inject(ShakyCenterActivity activity);

    void inject(DynamicRecruitActivity activity);

    void inject(HeaderDetailActivity activity);

    void inject(WalletActivity_GY activity);

    void inject(DynamicListAppDetailActivity activity);

    void inject(SearchAppActivity activity);

    void inject(MarketQuotationActivity activity);

    void inject(MarketQuotationDetailActivity activity);

    void inject(GroupManagerSettingActivity activity);

    void inject(GroupAddManagerActivity activity);

    void inject(GroupQuestionAnswerActivity activity);

    void inject(GroupMentionedSeleteActivity activity);

    void inject(DynamicNoticeFlashActivity activity);

    void inject(DynamicFlashDetialActivity activity);

    void inject(PrivilegeActivity activity);

    void inject(RedHallActivity activity);

    void inject(RechargeVipActivity activity);

    void inject(RechargeVipSuccessActivity activity);

    void inject(RedHallDetailActivity activity);


    void inject(AddressPublicActivity activity);
}

