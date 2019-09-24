package com.bochat.app.business;

import com.bochat.app.business.login.LoginPresenter;
import com.bochat.app.business.login.RegisterPresenter;
import com.bochat.app.business.login.SplashScreenPresenter;
import com.bochat.app.business.main.BoChatPresenter;
import com.bochat.app.business.main.bill.GCSpecialCodePresenter;
import com.bochat.app.business.main.bill.OrderListMinePresenter;
import com.bochat.app.business.main.bill.OrderListPresenter;
import com.bochat.app.business.main.bill.PropertyCashOutPresenter;
import com.bochat.app.business.main.bill.PropertyRechargePresenter;
import com.bochat.app.business.main.bill.PropertyRechargeResultPresenter;
import com.bochat.app.business.main.bill.QuickExchangeDetailPresenter;
import com.bochat.app.business.main.bill.QuickExchangeHallPresenter;
import com.bochat.app.business.main.bill.QuickExchangePresenter;
import com.bochat.app.business.main.bill.TestIncomeOfTodayPresenter;
import com.bochat.app.business.main.bill.TokenDetailPresenter;
import com.bochat.app.business.main.bill.TokenPropertyPresenter;
import com.bochat.app.business.main.bill.TokenSelectPresenter;
import com.bochat.app.business.main.bill.TokenTransferPresenter;
import com.bochat.app.business.main.bill.TokenTransferReceivePresenter;
import com.bochat.app.business.main.bill.TokenTransferSelectCoinPresenter;
import com.bochat.app.business.main.bill.WalletPresenter;
import com.bochat.app.business.main.bill.WalletPropertyPresenter;
import com.bochat.app.business.main.book.AddressBlackListPresenter;
import com.bochat.app.business.main.book.AddressBookPresenter;
import com.bochat.app.business.main.book.AddressGroupPresenter;
import com.bochat.app.business.main.book.AddressPublicNumPresenter;
import com.bochat.app.business.main.book.AddressTeamPresenter;
import com.bochat.app.business.main.book.AddressUserPresenter;
import com.bochat.app.business.main.book.ApplyListPresenter;
import com.bochat.app.business.main.book.DealAddFriendPresenter;
import com.bochat.app.business.main.book.FriendApplyPresenter;
import com.bochat.app.business.main.book.GroupApplyPresenter;
import com.bochat.app.business.main.book.HeaderDetailPresenter;
import com.bochat.app.business.main.book.SearchAddressBookPresenter;
import com.bochat.app.business.main.conversation.AddFriendPresenter;
import com.bochat.app.business.main.conversation.ConversationDetailPresenter;
import com.bochat.app.business.main.conversation.ConversationPresenter;
import com.bochat.app.business.main.conversation.EditAddFriendApplyPresenter;
import com.bochat.app.business.main.conversation.FriendDetailPresenter;
import com.bochat.app.business.main.conversation.FriendManageNotePresenter;
import com.bochat.app.business.main.conversation.FriendManagePresenter;
import com.bochat.app.business.main.conversation.GroupAddManagerPresenter;
import com.bochat.app.business.main.conversation.GroupChatPresenter;
import com.bochat.app.business.main.conversation.GroupDetailPresenter;
import com.bochat.app.business.main.conversation.GroupManageEditNamePresenter;
import com.bochat.app.business.main.conversation.GroupManageEditPresenter;
import com.bochat.app.business.main.conversation.GroupManageForbiddenPresenter;
import com.bochat.app.business.main.conversation.GroupManageJoinFilterPresenter;
import com.bochat.app.business.main.conversation.GroupManageManagerPresenter;
import com.bochat.app.business.main.conversation.GroupManagePresenter;
import com.bochat.app.business.main.conversation.GroupManageUpgradePresenter;
import com.bochat.app.business.main.conversation.GroupManagerSettingPresenter;
import com.bochat.app.business.main.conversation.GroupMemberManageNotePresenter;
import com.bochat.app.business.main.conversation.GroupMemberManagePresenter;
import com.bochat.app.business.main.conversation.GroupMemberPresenter;
import com.bochat.app.business.main.conversation.GroupMentionedSeletePresenter;
import com.bochat.app.business.main.conversation.GroupQuestionAnswerPresenter;
import com.bochat.app.business.main.conversation.QRCardPresenter;
import com.bochat.app.business.main.conversation.SearchConversationPresenter;
import com.bochat.app.business.main.conversation.SearchFriendPresenter;
import com.bochat.app.business.main.conversation.SearchGroupPresenter;
import com.bochat.app.business.main.conversation.SearchMessagePresenter;
import com.bochat.app.business.main.conversation.SelectFriendPresenter;
import com.bochat.app.business.main.conversation.SingleChatPresenter;
import com.bochat.app.business.main.dynamic.DynamicFlashDetialPresenter;
import com.bochat.app.business.main.dynamic.DynamicListAppDetailPresenter;
import com.bochat.app.business.main.dynamic.DynamicNoticeDetailPresenter;
import com.bochat.app.business.main.dynamic.DynamicNoticeFlashPresenter;
import com.bochat.app.business.main.dynamic.DynamicNoticePresenter;
import com.bochat.app.business.main.dynamic.DynamicPresenter;
import com.bochat.app.business.main.dynamic.DynamicRecruitPresenter;
import com.bochat.app.business.main.dynamic.DynamicFlashPresenter;
import com.bochat.app.business.main.dynamic.FastSpeedPresenter;
import com.bochat.app.business.main.dynamic.KChatPresenter;
import com.bochat.app.business.main.dynamic.ListAppActivityPresenter;
import com.bochat.app.business.main.dynamic.ListAppPresenter;
import com.bochat.app.business.main.dynamic.MarketQuotationBuyAndSellPresenter;
import com.bochat.app.business.main.dynamic.MarketQuotationDetailPresenter;
import com.bochat.app.business.main.dynamic.MarketQuotationEntrustPagerPresenter;
import com.bochat.app.business.main.dynamic.MarketQuotationEntrustPresenter;
import com.bochat.app.business.main.dynamic.MarketQuotationOptionalPresenter;
import com.bochat.app.business.main.dynamic.MarketQuotationPresenter;
import com.bochat.app.business.main.dynamic.PrivilegePresenter;
import com.bochat.app.business.main.dynamic.ProjectIdentificationPresenter;
import com.bochat.app.business.main.dynamic.ProjectIdentificationResultPresenter;
import com.bochat.app.business.main.dynamic.ProtocolBookPresenter;
import com.bochat.app.business.main.dynamic.RechargePresenter;
import com.bochat.app.business.main.dynamic.RechargeVipSuccessPresenter;
import com.bochat.app.business.main.dynamic.RedHallDetailPresenter;
import com.bochat.app.business.main.dynamic.RedHallPresenter;
import com.bochat.app.business.main.dynamic.SearchAppPresenter;
import com.bochat.app.business.main.dynamic.ShakyCenterPresenter;
import com.bochat.app.business.main.dynamic.ViewLookSearchPresenter;
import com.bochat.app.business.main.dynamic.ViewSpotPresenter;
import com.bochat.app.business.main.dynamic.WebPresenter;
import com.bochat.app.business.main.mine.AboutUsPresenter;
import com.bochat.app.business.main.mine.BankCardAddPresenter;
import com.bochat.app.business.main.mine.BankCardPresenter;
import com.bochat.app.business.main.mine.BankSelectPresenter;
import com.bochat.app.business.main.mine.BillDetailPresenter;
import com.bochat.app.business.main.mine.BillPresenter;
import com.bochat.app.business.main.mine.DevelopingPresenter;
import com.bochat.app.business.main.mine.EditPasswordPresenter;
import com.bochat.app.business.main.mine.EditPayPasswordPresenter;
import com.bochat.app.business.main.mine.EditUserInfoPresenter;
import com.bochat.app.business.main.mine.GetVerifyCodePresenter;
import com.bochat.app.business.main.mine.InvitationPresenter;
import com.bochat.app.business.main.mine.MinePresenter;
import com.bochat.app.business.main.mine.RealNameAuthPresenter;
import com.bochat.app.business.main.mine.RealNameInfoPresenter;
import com.bochat.app.business.main.mine.SetPasswordPresenter;
import com.bochat.app.business.main.mine.SetPayPasswordPresenter;
import com.bochat.app.business.main.mine.SettingsPhoneInfoPresenter;
import com.bochat.app.business.main.mine.SettingsPresenter;
import com.bochat.app.business.main.redpacket.BidListPresenter;
import com.bochat.app.business.main.redpacket.CandyPresenter;
import com.bochat.app.business.main.redpacket.RedpacketDetailPresenter;
import com.bochat.app.business.main.redpacket.SendRedPacketPresenter;
import com.bochat.app.business.main.redpacket.SmallMoneyPresenter;
import com.bochat.app.common.contract.AddressTeamContract;
import com.bochat.app.common.contract.DevelopingContract;
import com.bochat.app.common.contract.LoginContract;
import com.bochat.app.common.contract.MainContract;
import com.bochat.app.common.contract.RegisterContract;
import com.bochat.app.common.contract.SplashScreenContract;
import com.bochat.app.common.contract.bill.GCSpecialCodeContract;
import com.bochat.app.common.contract.bill.OrderListContract;
import com.bochat.app.common.contract.bill.OrderListMineContract;
import com.bochat.app.common.contract.bill.PropertyCashOutContract;
import com.bochat.app.common.contract.bill.PropertyRechargeContract;
import com.bochat.app.common.contract.bill.PropertyRechargeResultContract;
import com.bochat.app.common.contract.bill.QuickExchangeContract;
import com.bochat.app.common.contract.bill.QuickExchangeDetailContract;
import com.bochat.app.common.contract.bill.QuickExchangeHallContract;
import com.bochat.app.common.contract.bill.TokenDetailContract;
import com.bochat.app.common.contract.bill.TokenPropertyContract;
import com.bochat.app.common.contract.bill.TokenSelectContract;
import com.bochat.app.common.contract.bill.TokenTransferContract;
import com.bochat.app.common.contract.bill.TokenTransferReceiveContract;
import com.bochat.app.common.contract.bill.TokenTransferSelectCoinContract;
import com.bochat.app.common.contract.bill.WalletContract;
import com.bochat.app.common.contract.bill.WalletPropertyContract;
import com.bochat.app.common.contract.book.AddressPublicContract;
import com.bochat.app.common.contract.book.AddressBookContract;
import com.bochat.app.common.contract.book.AddressGroupContract;
import com.bochat.app.common.contract.book.AddressPublicNumContract;
import com.bochat.app.common.contract.book.AddressUserContract;
import com.bochat.app.common.contract.book.ApplyListContract;
import com.bochat.app.common.contract.book.DealAddFriendContract;
import com.bochat.app.common.contract.book.FriendApplyContract;
import com.bochat.app.common.contract.book.GroupApplyContract;
import com.bochat.app.common.contract.book.SearchAddressBookContract;
import com.bochat.app.common.contract.conversation.AddFriendContract;
import com.bochat.app.common.contract.conversation.ConversationContract;
import com.bochat.app.common.contract.conversation.ConversationDetailContract;
import com.bochat.app.common.contract.conversation.EditAddFriendApplyContract;
import com.bochat.app.common.contract.conversation.FriendDetailContact;
import com.bochat.app.common.contract.conversation.FriendManageContract;
import com.bochat.app.common.contract.conversation.FriendManageNoteContract;
import com.bochat.app.common.contract.conversation.GroupAddManagerContract;
import com.bochat.app.common.contract.conversation.GroupChatContract;
import com.bochat.app.common.contract.conversation.GroupDetailContract;
import com.bochat.app.common.contract.conversation.GroupManageContract;
import com.bochat.app.common.contract.conversation.GroupManageEditContract;
import com.bochat.app.common.contract.conversation.GroupManageEditNameContract;
import com.bochat.app.common.contract.conversation.GroupManageForbiddenContract;
import com.bochat.app.common.contract.conversation.GroupManageJoinFilterContract;
import com.bochat.app.common.contract.conversation.GroupManageManagerContract;
import com.bochat.app.common.contract.conversation.GroupManageUpgradeContract;
import com.bochat.app.common.contract.conversation.GroupManagerSettingContract;
import com.bochat.app.common.contract.conversation.GroupMemberContract;
import com.bochat.app.common.contract.conversation.GroupMemberManageContract;
import com.bochat.app.common.contract.conversation.GroupMemberManageNoteContract;
import com.bochat.app.common.contract.conversation.GroupMentionedSeleteContract;
import com.bochat.app.common.contract.conversation.GroupQuestionAnswerContract;
import com.bochat.app.common.contract.conversation.HeaderDetailContract;
import com.bochat.app.common.contract.conversation.QRCardContract;
import com.bochat.app.common.contract.conversation.SearchConversationContract;
import com.bochat.app.common.contract.conversation.SearchFriendContract;
import com.bochat.app.common.contract.conversation.SearchGroupContract;
import com.bochat.app.common.contract.conversation.SearchMessageContract;
import com.bochat.app.common.contract.conversation.SelectFriendContract;
import com.bochat.app.common.contract.conversation.SingleChatContract;
import com.bochat.app.common.contract.dynamic.DynamicContract;
import com.bochat.app.common.contract.dynamic.DynamicFlashContract;
import com.bochat.app.common.contract.dynamic.DynamicFlashDetialContract;
import com.bochat.app.common.contract.dynamic.DynamicListAppDetailContract;
import com.bochat.app.common.contract.dynamic.DynamicNoticeDetailContract;
import com.bochat.app.common.contract.dynamic.DynamicNoticeFlashContract;
import com.bochat.app.common.contract.dynamic.DynamicNoticeFraContract;
import com.bochat.app.common.contract.dynamic.DynamicRecruitContract;
import com.bochat.app.common.contract.dynamic.FastSpeedContract;
import com.bochat.app.common.contract.dynamic.KChatContract;
import com.bochat.app.common.contract.dynamic.ListAppActContract;
import com.bochat.app.common.contract.dynamic.ListAppContract;
import com.bochat.app.common.contract.dynamic.MarketQuotationBuyAndSellContract;
import com.bochat.app.common.contract.dynamic.MarketQuotationContract;
import com.bochat.app.common.contract.dynamic.MarketQuotationDetailContract;
import com.bochat.app.common.contract.dynamic.MarketQuotationEntrustContract;
import com.bochat.app.common.contract.dynamic.MarketQuotationEntrustPagerContract;
import com.bochat.app.common.contract.dynamic.MarketQuotationOptionalContract;
import com.bochat.app.common.contract.dynamic.PrivilegeContract;
import com.bochat.app.common.contract.dynamic.ProjectIdentificationContract;
import com.bochat.app.common.contract.dynamic.ProjectIdentificationResultContract;
import com.bochat.app.common.contract.dynamic.ProtocolBookContract;
import com.bochat.app.common.contract.dynamic.RechargeContract;
import com.bochat.app.common.contract.dynamic.RechargeVipSuccessContract;
import com.bochat.app.common.contract.dynamic.RedHallContract;
import com.bochat.app.common.contract.dynamic.RedHallDetailContract;
import com.bochat.app.common.contract.dynamic.SearchAppContract;
import com.bochat.app.common.contract.dynamic.ShakyCenterContract;
import com.bochat.app.common.contract.dynamic.ViewLookSearchContract;
import com.bochat.app.common.contract.dynamic.ViewSpotContract;
import com.bochat.app.common.contract.dynamic.WebContract;
import com.bochat.app.common.contract.mine.AboutUsContract;
import com.bochat.app.common.contract.mine.AddBankCardContract;
import com.bochat.app.common.contract.mine.BankCardContract;
import com.bochat.app.common.contract.mine.BillContract;
import com.bochat.app.common.contract.mine.BillDetailContract;
import com.bochat.app.common.contract.mine.EditPasswordContract;
import com.bochat.app.common.contract.mine.EditPayPasswordContract;
import com.bochat.app.common.contract.mine.EditUserInfoContract;
import com.bochat.app.common.contract.mine.GetVerifyCodeContract;
import com.bochat.app.common.contract.mine.InvitationContract;
import com.bochat.app.common.contract.mine.MineContract;
import com.bochat.app.common.contract.mine.RealNameAuthContract;
import com.bochat.app.common.contract.mine.RealNameInfoContract;
import com.bochat.app.common.contract.mine.SelectBankContract;
import com.bochat.app.common.contract.mine.SetPasswordContract;
import com.bochat.app.common.contract.mine.SetPayPasswordContract;
import com.bochat.app.common.contract.mine.SettingsContract;
import com.bochat.app.common.contract.mine.SettingsPhoneInfoContract;
import com.bochat.app.common.contract.readpacket.BidListContract;
import com.bochat.app.common.contract.readpacket.CandyContract;
import com.bochat.app.common.contract.readpacket.RedPacketDetailContract;
import com.bochat.app.common.contract.readpacket.SendRedPacketContract;
import com.bochat.app.common.contract.readpacket.SmallMoneyContract;
import com.bochat.app.common.contract.test.TestIncomeOfTodayContract;
import com.bochat.app.mvp.injector.scope.ActivityScope;
import com.bochat.app.mvp.injector.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 09:50
 * Description :
 */
@Module
public class BusinessModule {

    @Provides
    @ActivityScope
    public LoginContract.Presenter provideLoginPresenter() {
        return new LoginPresenter();
    }

    @Provides
    @ActivityScope
    public MainContract.Presenter provideMainPresenter() {
        return new BoChatPresenter();
    }

    @Provides
    @ActivityScope
    public RegisterContract.Presenter provideRegisterPresenter() {
        return new RegisterPresenter();
    }

    @Provides
    @ActivityScope
    public SplashScreenContract.Presenter provideSplashScreenPresenter() {
        return new SplashScreenPresenter();
    }

    @Provides
    @ActivityScope
    public EditAddFriendApplyContract.Presenter provideEditRequestPresenter() {
        return new EditAddFriendApplyPresenter();
    }

    @Provides
    @FragmentScope
    public AddressBookContract.Presenter provideAddressBookPresenter() {
        return new AddressBookPresenter();
    }
    @Provides
    @ActivityScope
    public FriendDetailContact.Presenter provideFriendDetailPresenter() {
        return new FriendDetailPresenter();
    }
    @Provides
    @ActivityScope
    public ApplyListContract.Presenter provideApplyListPresenter() {
        return new ApplyListPresenter();
    }
    @Provides
    @FragmentScope
    public FriendApplyContract.Presenter provideFriendApplyPresenter() {
        return new FriendApplyPresenter();
    }
    @Provides
    @FragmentScope
    public AddressUserContract.Presenter provideAddressUserPresenter() {
        return new AddressUserPresenter();
    }
    @Provides
    @FragmentScope
    public AddressGroupContract.Presenter provideAddressGroupPresenter() {
        return new AddressGroupPresenter();
    }
    @Provides
    @FragmentScope
    public AddressPublicContract.Presenter provideAddressBlackListPresenter() {
        return new AddressBlackListPresenter();
    }
    @Provides
    @FragmentScope
    public GroupApplyContract.Presenter provideGroupApplyPresenter() {
        return new GroupApplyPresenter();
    }
    @Provides
    @ActivityScope
    public DealAddFriendContract.Presenter provideDealAddFriendPresenter() {
        return new DealAddFriendPresenter();
    }
    @Provides
    @FragmentScope
    public ConversationContract.Presenter provideConversationPresenter() {
        return new ConversationPresenter();
    }
    @Provides
    @ActivityScope
    public GroupDetailContract.Presenter provideGroupDetailPresenter() {
        return new GroupDetailPresenter();
    }
    @Provides
    @ActivityScope
    public SelectFriendContract.Presenter provideSelectFriendPresenter() {
        return new SelectFriendPresenter();
    }
    @Provides
    @FragmentScope
    public GroupChatContract.Presenter provideGroupChatPresenter() {
        return new GroupChatPresenter();
    }
    @Provides
    @FragmentScope
    public SingleChatContract.Presenter provideSingleChatPresenter() {
        return new SingleChatPresenter();
    }
    @Provides
    @ActivityScope
    public QRCardContract.Presenter provideQRCardPresenter() {
        return new QRCardPresenter();
    }
    @Provides
    @ActivityScope
    public AddFriendContract.Presenter provideAddFriendPresenter() {
        return new AddFriendPresenter();
    }
    @Provides
    @ActivityScope
    public SearchFriendContract.Presenter provideSearchFriendPresenter() {
        return new SearchFriendPresenter();
    }
    @Provides
    @ActivityScope
    public SearchConversationContract.Presenter provideSearchConversationPresenter() {
        return new SearchConversationPresenter();
    }
    @Provides
    @ActivityScope
    public ConversationDetailContract.Presenter provideConversationDetailPresenter() {
        return new ConversationDetailPresenter();
    }
    
    @Provides
    @ActivityScope
    public GroupManageEditNameContract.Presenter provideGroupManageEditNamePresenter() {
        return new GroupManageEditNamePresenter();
    }
    @Provides
    @ActivityScope
    public GroupManageEditContract.Presenter provideGroupManageEditPresenter() {
        return new GroupManageEditPresenter();
    }
    @Provides
    @ActivityScope
    public GroupManageForbiddenContract.Presenter provideGroupManageForbiddenPresenter() {
        return new GroupManageForbiddenPresenter();
    }
    @Provides
    @ActivityScope
    public GroupManageContract.Presenter provideGroupManagePresenter() {
        return new GroupManagePresenter();
    }
    @Provides
    @ActivityScope
    public GroupManageUpgradeContract.Presenter provideGroupManageUpgradePresenter() {
        return new GroupManageUpgradePresenter();
    }
    @Provides
    @ActivityScope
    public GroupMemberManageNoteContract.Presenter provideGroupMemberManageNotePresenter() {
        return new GroupMemberManageNotePresenter();
    }
    @Provides
    @ActivityScope
    public GroupMemberManageContract.Presenter provideGroupMemberManagePresenter() {
        return new GroupMemberManagePresenter();
    }
    @Provides
    @ActivityScope
    public GroupMemberContract.Presenter provideGroupMemberPresenter() {
        return new GroupMemberPresenter();
    }
    @Provides
    @FragmentScope
    public MineContract.Presenter provideMinePresenter() {
        return new MinePresenter();
    }
    @Provides
    @ActivityScope
    public FriendManageContract.Presenter provideFriendManagePresenter() {
        return new FriendManagePresenter();
    }
    @Provides
    @ActivityScope
    public FriendManageNoteContract.Presenter provideFriendManageNotePresenter() {
        return new FriendManageNotePresenter();
    }
    @Provides
    @ActivityScope
    public SearchMessageContract.Presenter provideSearchMessagePresenter() {
        return new SearchMessagePresenter();
    }
    @Provides
    @ActivityScope
    public ViewLookSearchContract.Presenter provideViewLookSearchPresenter() {
        return new ViewLookSearchPresenter();
    }
    @Provides
    @FragmentScope
    public DynamicContract.Presenter provideDynamicPresenter() {
        return new DynamicPresenter();
    }

    @Provides
    @ActivityScope
    public WebContract.Presenter provideWebPresenter() {
        return new WebPresenter();
    }

    @Provides
    @FragmentScope
    public ListAppContract.Presenter provideListAppPresenter() {
        return new ListAppPresenter();
    }

    @Provides
    @ActivityScope
    public ListAppActContract.Presenter provideListAppActivityPresenter() {
        return new ListAppActivityPresenter();
    }

    @Provides
    @FragmentScope
    public DynamicNoticeFraContract.Presenter provideDynamicNoticePresenter() {
        return new DynamicNoticePresenter();
    }

    @Provides
    @FragmentScope
    public DynamicFlashContract.Presenter provideDynmicFlashPresenter() {
        return new DynamicFlashPresenter();
    }

    @Provides
    @ActivityScope
    public SendRedPacketContract.Presenter provideSendRedPacketPresenter() {
        return new SendRedPacketPresenter();
    }

    @Provides
    @FragmentScope
    public CandyContract.Presenter provideCandyPresenter() {
        return new CandyPresenter();
    }

    @Provides
    @FragmentScope
    public SmallMoneyContract.Presenter provideSmallMoneyPresenter() {
        return new SmallMoneyPresenter();
    }

    @Provides
    @ActivityScope
    public BidListContract.Presenter provideBidListPresenter() {
        return new BidListPresenter();
    }

    @Provides
    @ActivityScope
    public RedPacketDetailContract.Presenter provideRedpacketDetailPresenter() {
        return new RedpacketDetailPresenter();
    }
    @Provides
    @FragmentScope
    public DevelopingContract.Presenter provideDevelopingPresenter() {
        return new DevelopingPresenter();
    }

    @Provides
    @ActivityScope
    public WalletContract.Presenter provideWalletPresenter() {
        return new WalletPresenter();
    }

    @Provides
    @ActivityScope
    public PropertyRechargeContract.Presenter provideTopUpPresenter() {
        return new PropertyRechargePresenter();
    }

    @Provides
    @ActivityScope
    public PropertyCashOutContract.Presenter provideWithdrawDepositPresenter() {
        return new PropertyCashOutPresenter();
    }

    @Provides
    @FragmentScope
    public WalletPropertyContract.Presenter provideIChangePresenter() {
        return new WalletPropertyPresenter();
    }

    @Provides
    @FragmentScope
    public TokenPropertyContract.Presenter provideTokenPropertyPresenter() {
        return new TokenPropertyPresenter();
    }


    @Provides
    @ActivityScope
    public GCSpecialCodeContract.Presenter provideSpecialCodePresenter() {
        return new GCSpecialCodePresenter();
    }
    
    @Provides
    @ActivityScope
    public TokenTransferReceiveContract.Presenter provideReceiveCurrencyPresenter() {
        return new TokenTransferReceivePresenter();
    }

    @Provides
    @ActivityScope
    public TokenTransferSelectCoinContract.Presenter provideCurrencyOptionPresenter() {
        return new TokenTransferSelectCoinPresenter();
    }

    @Provides
    @ActivityScope
    public TokenTransferContract.Presenter provideTransferPresenter() {
        return new TokenTransferPresenter();
    }


    @Provides
    @ActivityScope
    public TokenDetailContract.Presenter provideDetailsCurrencyPresenter() {
        return new TokenDetailPresenter();
    }

    @Provides
    @ActivityScope
    public OrderListContract.Presenter provideMyOrderPresenter() {
        return new OrderListPresenter();
    }

    @Provides
    @FragmentScope
    public OrderListMineContract.Presenter provideChangePresenter() {
        return new OrderListMinePresenter();
    }

    @Provides
    @ActivityScope
    public QuickExchangeDetailContract.Presenter provideDealClosePresenter() {
        return new QuickExchangeDetailPresenter();
    }

    @Provides
    @ActivityScope
    public QuickExchangeHallContract.Presenter provideChangeHallPresenter() {
        return new QuickExchangeHallPresenter();
    }
    @Provides
    @ActivityScope
    public AboutUsContract.Presenter provideAboutUsPresenter() {
        return new AboutUsPresenter();
    }
    @Provides
    @ActivityScope
    public QuickExchangeContract.Presenter provideQuickExchangePresenter() {
        return new QuickExchangePresenter();
    }
    @Provides
    @ActivityScope
    public PropertyRechargeResultContract.Presenter providePropertyRechargeResultPresenter() {
        return new PropertyRechargeResultPresenter();
    }
    @Provides
    @ActivityScope
    public BillContract.Presenter provideBillPresenter() {
        return new BillPresenter();
    }
    @Provides
    @ActivityScope
    public BillDetailContract.Presenter provideBillDetailPresenter() {
        return new BillDetailPresenter();
    }
    @Provides
    @ActivityScope
    public AddBankCardContract.Presenter provideBankCardAddPresenter() {
        return new BankCardAddPresenter();
    }
    @Provides
    @ActivityScope
    public SelectBankContract.Presenter provideBankSelectPresenter() {
        return new BankSelectPresenter();
    }
    @Provides
    @ActivityScope
    public RealNameAuthContract.Presenter provideRealNameAuthPresenter() {
        return new RealNameAuthPresenter();
    }
    @Provides
    @ActivityScope
    public RealNameInfoContract.Presenter provideRealNameInfoPresenter() {
        return new RealNameInfoPresenter();
    }
    @Provides
    @ActivityScope
    public EditUserInfoContract.Presenter provideEditUserInfoPresenter() {
        return new EditUserInfoPresenter();
    }
    @Provides
    @ActivityScope
    public SearchAddressBookContract.Presenter provideSearchAddressBookPresenter() {
        return new SearchAddressBookPresenter();
    }
    
    @Provides
    @ActivityScope
    public EditPasswordContract.Presenter provideSettingsEditPasswordPresenter() {
        return new EditPasswordPresenter();
    }
    @Provides
    @ActivityScope
    public GetVerifyCodeContract.Presenter provideSettingsGetCodePresenter() {
        return new GetVerifyCodePresenter();
    }
    @Provides
    @ActivityScope
    public SettingsPhoneInfoContract.Presenter provideSettingsPhoneInfoPresenter() {
        return new SettingsPhoneInfoPresenter();
    }
    @Provides
    @ActivityScope
    public SettingsContract.Presenter provideSettingsPresenter() {
        return new SettingsPresenter();
    }
    @Provides
    @ActivityScope
    public SetPasswordContract.Presenter provideSettingsSetPasswordPresenter() {
        return new SetPasswordPresenter();
    }
    @Provides
    @ActivityScope
    public SetPayPasswordContract.Presenter provideSetPayPasswordPresenter() {
        return new SetPayPasswordPresenter();
    }
    @Provides
    @ActivityScope
    public EditPayPasswordContract.Presenter provideEditPayPasswordPresenter() {
        return new EditPayPasswordPresenter();
    }
    @Provides
    @ActivityScope
    public BankCardContract.Presenter provideBankCardPresenter() {
        return new BankCardPresenter();
    }
    @Provides
    @ActivityScope
    public InvitationContract.Presenter provideInvitationPresenter() {
        return new InvitationPresenter();
    }
    @Provides
    @ActivityScope
    public DynamicNoticeDetailContract.Presenter provideDynamicNoticeDetailPresenter() {
        return new DynamicNoticeDetailPresenter();
    }
    @Provides
    @ActivityScope
    public ViewSpotContract.Presenter provideViewSpotPresenter() {
        return new ViewSpotPresenter();
    }
    @Provides
    @ActivityScope
    public ProjectIdentificationContract.Presenter provideProjectIdentificationPresenter() {
        return new ProjectIdentificationPresenter();
    }
    @Provides
    @ActivityScope
    public ProjectIdentificationResultContract.Presenter provideProjectIdentificationResultPresenter() {
        return new ProjectIdentificationResultPresenter();
    }
    @Provides
    @ActivityScope
    public ProtocolBookContract.Presenter provideProtocolBookPresenter() {
        return new ProtocolBookPresenter();
    }

    @Provides
    @ActivityScope
    public SearchGroupContract.Presenter privideSearchGroupPresenter() {
        return new SearchGroupPresenter();
    }
    @Provides
    @ActivityScope
    public GroupManageJoinFilterContract.Presenter privideGroupManageJoinFilterPresenter() {
        return new GroupManageJoinFilterPresenter();
    }
    @Provides
    @ActivityScope
    public GroupManageManagerContract.Presenter privideGroupManageManagerPresenter() {
        return new GroupManageManagerPresenter();
    }
    @Provides
    @ActivityScope
    public TestIncomeOfTodayContract.Presenter privideTestIncomeOfTodayPresenter() {
        return new TestIncomeOfTodayPresenter();
    }
    @Provides
    @ActivityScope
    public FastSpeedContract.Presenter privideFastSpeedPresenter() {
        return new FastSpeedPresenter();
    }
    @Provides
    @ActivityScope
    public TokenSelectContract.Presenter provideTokenSelectPresenter() {
        return new TokenSelectPresenter();
    }
    @Provides
    @ActivityScope
    public ShakyCenterContract.Presenter provideShakyCenterPresenter() {
        return new ShakyCenterPresenter();
    }

    @Provides
    @ActivityScope
    public DynamicRecruitContract.Presenter provideDynamicRecruitPresenter() {
        return new DynamicRecruitPresenter();
    }

    @Provides
    @FragmentScope
    public KChatContract.Presenter provideDynamicKChatPresenter() {
        return new KChatPresenter();
    }


    @Provides
    @ActivityScope
    public HeaderDetailContract.Presenter provideHeaderDetailPresenter() {
        return new HeaderDetailPresenter();
    }

   @Provides
   @ActivityScope
   public GroupManagerSettingContract.Presenter provideGroupManagerSettingPresenter(){
        return new GroupManagerSettingPresenter();
   }

   @Provides
   @ActivityScope
   public GroupAddManagerContract.Presenter provideGroupAddManagerPresenter(){
        return new GroupAddManagerPresenter();
   }


    @Provides
    @ActivityScope
    public DynamicListAppDetailContract.Presenter provideDynamicListAppDetailPresenter() {
        return new DynamicListAppDetailPresenter();
    }

    @Provides
    @ActivityScope
    public SearchAppContract.Presenter provideSearchAppPresenter() {
        return new SearchAppPresenter();
    }

    @Provides
    @ActivityScope
    public MarketQuotationContract.Presenter provideMarketQuotationPresenter() {
        return new MarketQuotationPresenter();
    }

    @Provides
    @FragmentScope
    public MarketQuotationOptionalContract.Presenter provideMarketQuotationOptionalPresenter() {
        return new MarketQuotationOptionalPresenter();
    }

    @Provides
    @ActivityScope
    public MarketQuotationDetailContract.Presenter provideMarketQuotationDetailPresenter() {
        return new MarketQuotationDetailPresenter();
    }

    @Provides
    @FragmentScope
    public MarketQuotationBuyAndSellContract.Presenter provideMarketQuotationBuyAndSellPresenter() {
        return new MarketQuotationBuyAndSellPresenter();
    }

    @Provides
    @FragmentScope
    public MarketQuotationEntrustContract.Presenter provideMarketQuotationEntrustPresenter() {
        return new MarketQuotationEntrustPresenter();
    }

    @Provides
    @ActivityScope
    public GroupQuestionAnswerContract.Presenter provideGroupQuestionAnswerPresenter() {
        return new GroupQuestionAnswerPresenter();
    }

    @Provides
    @FragmentScope
    public MarketQuotationEntrustPagerContract.Presenter provideMarketQuotationEntrustPagerPresenter() {
        return new MarketQuotationEntrustPagerPresenter();
    }


    @Provides
    @ActivityScope
    public GroupMentionedSeleteContract.Presenter provideGroupMentionedSeletePresenter() {
        return new GroupMentionedSeletePresenter();
    }

    @Provides
    @ActivityScope
    public DynamicNoticeFlashContract.Presenter provideDynamicNoticeFlashPresenter() {
        return new DynamicNoticeFlashPresenter();
    }
    @Provides
    @ActivityScope
    public DynamicFlashDetialContract.Presenter provideDynamicFlashDetialPresenter() {
        return new DynamicFlashDetialPresenter();
    }

    @Provides
    @ActivityScope
    public PrivilegeContract.Presenter providePrivilegePresenter() {
        return new PrivilegePresenter();
    }
    @Provides
    @ActivityScope
    public RedHallContract.Presenter provideRedHallPresenter() {
        return new RedHallPresenter();
    }
    @Provides
    @ActivityScope
    public RechargeContract.Presenter provideRechargePresenter() {
        return new RechargePresenter();
    }
    @Provides
    @ActivityScope
    public RechargeVipSuccessContract.Presenter provideRechargeVipSuccessPresenter() {
        return new RechargeVipSuccessPresenter();
    }
    @Provides
    @ActivityScope
    public RedHallDetailContract.Presenter provideRedHallDetailPresenter() {
        return new RedHallDetailPresenter();
    }


    @Provides
    @FragmentScope
    public AddressTeamContract.Presenter provideAddressTeamPresenter() {
        return new AddressTeamPresenter();
    }

    @Provides
    @ActivityScope
    public AddressPublicNumContract.Presenter provideAddressPublicPresenter() {
        return new AddressPublicNumPresenter();
    }


}