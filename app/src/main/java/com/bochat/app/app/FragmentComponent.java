package com.bochat.app.app;

import android.content.Context;

import com.bochat.app.app.fragment.AddressBookFragment;
import com.bochat.app.app.fragment.AddressGroupFragment;
import com.bochat.app.app.fragment.AddressTeamFragment;
import com.bochat.app.app.fragment.AddressUserFragment;
import com.bochat.app.app.fragment.CandyFragment;
import com.bochat.app.app.fragment.ConversationFragment;
import com.bochat.app.app.fragment.DevelopingFragment;
import com.bochat.app.app.fragment.FriendApplyListFragment;
import com.bochat.app.app.fragment.GroupApplyListFragment;
import com.bochat.app.app.fragment.ListAppFragment;
import com.bochat.app.app.fragment.SmallMoneyFragment;
import com.bochat.app.app.fragment.addressbook.AddressPublicFragment;
import com.bochat.app.app.fragment.bill.MineFragment;
import com.bochat.app.app.fragment.bill.OderListMineCreateFragment;
import com.bochat.app.app.fragment.bill.OrderListMineFinishFragment;
import com.bochat.app.app.fragment.bill.TokenPropertyFragment;
import com.bochat.app.app.fragment.bill.WalletPropertyFragment;
import com.bochat.app.app.fragment.dynamic.DynamicFlashFragment;
import com.bochat.app.app.fragment.dynamic.DynamicFragment;
import com.bochat.app.app.fragment.dynamic.DynamicNoticeFragment;
import com.bochat.app.app.fragment.dynamic.KChatFragment;
import com.bochat.app.app.fragment.dynamic.MarketQuotationBuyAndSellFragment;
import com.bochat.app.app.fragment.dynamic.MarketQuotationEntrustFragment;
import com.bochat.app.app.fragment.dynamic.MarketQuotationEntrustPagerFragment;
import com.bochat.app.app.fragment.dynamic.MarketQuotationOptionalFragment;
import com.bochat.app.business.BusinessModule;
import com.bochat.app.mvp.injector.ContextLife;
import com.bochat.app.mvp.injector.ContextLifeConst;
import com.bochat.app.mvp.injector.component.ApplicationComponent;
import com.bochat.app.mvp.injector.module.FragmentModule;
import com.bochat.app.mvp.injector.scope.FragmentScope;
import com.bochat.app.mvp.view.BaseActivity;

import dagger.Component;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 09:50
 * Description :
 */

@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = {FragmentModule.class, BusinessModule.class})
public interface FragmentComponent {

    BaseActivity getActivity();

    @ContextLife(ContextLifeConst.CONTEXT_ACTIVITY)
    Context getActivityContext();

    @ContextLife(ContextLifeConst.CONTEXT_APPLICATION)
    Context getApplicationContext();

    void inject(ConversationFragment fragment);

    void inject(AddressUserFragment fragment);

    void inject(AddressGroupFragment fragment);
    
    void inject(AddressBookFragment fragment);

    void inject(FriendApplyListFragment fragment);

    void inject(GroupApplyListFragment fragment);

    void inject(DynamicFragment fragment);

    void inject(ListAppFragment fragment);

    void inject(CandyFragment fragment);

    void inject(SmallMoneyFragment fragment);

    void inject(DevelopingFragment fragment);

    void inject(WalletPropertyFragment fragment);

    void inject(TokenPropertyFragment fragment);

    void inject(OderListMineCreateFragment fragment);

    void inject(OrderListMineFinishFragment fragment);

    void inject(MineFragment fragment);
    
    void inject(AddressPublicFragment fragment);

    void inject(KChatFragment fragment);

    void inject(MarketQuotationOptionalFragment fragment);

    void inject(MarketQuotationBuyAndSellFragment fragment);

    void inject(MarketQuotationEntrustFragment fragment);

    void inject(DynamicNoticeFragment fragment);

    void inject(DynamicFlashFragment fragment);

    void inject(MarketQuotationEntrustPagerFragment fragment);

    void inject(AddressTeamFragment fragment);
}