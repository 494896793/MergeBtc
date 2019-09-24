package com.bochat.app.app.activity;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;

import com.bochat.app.R;
import com.bochat.app.app.fragment.AddressBookFragment;
import com.bochat.app.app.fragment.ConversationFragment;
import com.bochat.app.app.fragment.bill.MineFragment;
import com.bochat.app.app.fragment.dynamic.DynamicFragment;
import com.bochat.app.app.fragment.dynamic.KChatFragment;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.view.FirstLoginDialog;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.MainContract;
import com.bochat.app.common.router.RouterBoChat;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.event.ConversationRefreshEvent;
import com.bochat.app.model.event.ReceiverMessageEvent;
import com.bochat.app.model.event.RongReconnectEvent;
import com.bochat.app.model.modelImpl.UserModule;
import com.bochat.app.model.receive.DownReceive;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/11 19:33
 * Description :
 */
@Route(path = RouterBoChat.PATH)
public class BoChatActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {

    @Inject
    MainContract.Presenter presenter;

    @BindView(R.id.bottom_nav_bar)
    BottomNavigationBar bottomNavigationBar;

    @BindView(R.id.bottom_nav_content)
    FrameLayout content;

    private SparseArray<Fragment> fragmentList;
    private FirstLoginDialog firstLoginDialog;

    ShapeBadgeItem mMessageBadge;
    private DownReceive receive;

    UserEntity userEntity;

    private BottomNavigationBar.OnTabSelectedListener listener = new BottomNavigationBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            try {
                if (fragmentList != null && position < fragmentList.size()) {
                    if (position == 3) {

                    } else {

                    }
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment fragment = fragmentList.get(position);
                    if (fragment.isAdded()) {
                        ft.show(fragment);
                    } else {
                        ft.add(R.id.bottom_nav_content, fragment);
                    }
                    ft.commitAllowingStateLoss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTabUnselected(int position) {
            try {
                if (fragmentList != null && fragmentList.size() > position) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment fragment = fragmentList.get(position);
                    ft.hide(fragment);
                    ft.commitAllowingStateLoss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTabReselected(int position) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        UserEntity userEntity = new UserModule().getLocalUserInfo();
        if (userEntity != null) {
            rongConnect(userEntity.getOther_id());
        }
        registBroad();
    }

    private void registBroad() {
        receive = new DownReceive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constan.DOWN_ACTION_STAR);
        intentFilter.addAction(Constan.DOWN_ACTION_DOWN_FINISH);
        intentFilter.addAction(Constan.DOWN_ACTION_PROGREE);
        registerReceiver(receive, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(receive);
    }

    @Subscribe
    public void onEventMainThread(RongReconnectEvent rongReconnectEvent) {
        if (rongReconnectEvent != null && rongReconnectEvent.rmToken != null) {
            rongConnect(rongReconnectEvent.rmToken);
        }
    }

    /**
     * 融云红点显示事件
     * @param event
     */
    @Subscribe
    public void onMessageReceiver(ReceiverMessageEvent event) {
        if (mMessageBadge != null) {
            if (event.isShowBadge)
                mMessageBadge.show();
            else
                mMessageBadge.hide();
        }
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected MainContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bochat);
    }

    @Override
    protected void initWidget() {
        userEntity = CachePool.getInstance().user().getLatest();
        
        if (userEntity != null && userEntity.getIsInit() == 1) {
            firstLoginDialog = new FirstLoginDialog(this);
            firstLoginDialog.setCancelable(false);
            firstLoginDialog.show();
            userEntity.setIsInit(2);
            CachePool.getInstance().user().put(userEntity);
        }
        
        mMessageBadge = new ShapeBadgeItem()
                .setShape(ShapeBadgeItem.SHAPE_OVAL)
                .setEdgeMarginInPixels(0)
                .setSizeInDp(this, 9, 9)
                .setAnimationDuration(200)
                .setShapeColor("#ffff4b3e");
        mMessageBadge.hide();

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_navigation_chat_focus, "消息").setBadgeItem(mMessageBadge)
                        .setInactiveIconResource(R.mipmap.ic_navigation_chat))
                .addItem(new BottomNavigationItem(R.mipmap.ic_navigation_book_focus, "通讯录")
                        .setInactiveIconResource(R.mipmap.ic_navigation_book))
                .addItem(new BottomNavigationItem(R.mipmap.ic_navigation_infomation_focus, "动态")
                        .setInactiveIconResource(R.mipmap.ic_navigation_infomation))
                .addItem(new BottomNavigationItem(R.mipmap.ic_navigation_mine_focus, "我的")
                        .setInactiveIconResource(R.mipmap.ic_navigation_mine))
                .initialise();
        int imagePx = ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_24);
        int textPx = ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_13);

        setBottomNavigationItem(bottomNavigationBar, imagePx, textPx);

        bottomNavigationBar.setTabSelectedListener(listener);
        initFragment();
        setDefaultFragment(0);
    }

    public void initFragment() {
        try {
            if (fragmentList == null) {
                fragmentList = new SparseArray(4);
            } else {
                fragmentList.clear();
            }
            ConversationFragment conversationFragment = new ConversationFragment();
            AddressBookFragment addressBookFragment = new AddressBookFragment();
            DynamicFragment developingFragment = new DynamicFragment();
            MineFragment mineFragment = new MineFragment();
//            KChatFragment developingFragment = new KChatFragment();

            fragmentList.put(0, conversationFragment);
            fragmentList.put(1, addressBookFragment);
            fragmentList.put(2, developingFragment);
            fragmentList.put(3, mineFragment);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rongConnect(String token) {
        LogUtil.LogDebug("connect  token=" + token);
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                Log.i(Constan.TAG, "融云 onTokenIncorrect");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                Log.i(Constan.TAG, "融云连接成功");
                EventBus.getDefault().post(new ConversationRefreshEvent());
                String head = "";
                if (userEntity.getHeadImg() != null) {
                    head = userEntity.getHeadImg();
                }
                UserInfo userInfo = new UserInfo(userid, userEntity.getNickname(), Uri.parse(head));
                RongIM.getInstance().setCurrentUserInfo(userInfo);
                RongIM.getInstance().refreshUserInfoCache(userInfo);
                RongIM.getInstance().setMessageAttachedUserInfo(true);
                initRedPackage();
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtil.LogError("融云 onError=" + errorCode);
            }
        });
    }

    private void initRedPackage() {


    }

    private void setDefaultFragment(int position) {
        try {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(R.id.bottom_nav_content, fragmentList.get(position));
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBottomNavigationItem(BottomNavigationBar bottomNavigationBar, int imgLen, int textSize) {
        try {
            Class barClass = bottomNavigationBar.getClass();
            Field[] fields = barClass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);



                if (field.getName().equals("mContainer")) {
                    try {
                        FrameLayout mainContainer = (FrameLayout) field.get(bottomNavigationBar);
                        FrameLayout.LayoutParams mainLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_56));
                        mainContainer.setLayoutParams(mainLayoutParams);
                        mainContainer.setPadding(ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_12),
                                ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_7),
                                ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_12),
                                ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_3));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (field.getName().equals("mTabContainer")) {
                    try {
                        //反射得到 mTabContainer
                        LinearLayout tabContainer = (LinearLayout) field.get(bottomNavigationBar);

                        FrameLayout.LayoutParams mainLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        tabContainer.setLayoutParams(mainLayoutParams);

                        for (int j = 0; j < tabContainer.getChildCount(); j++) {
                            //获取到容器内的各个Tab
                            View view = tabContainer.getChildAt(j);
                            //获取到Tab内的各个显示控件
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            FrameLayout container = (FrameLayout) view.findViewById(R.id.fixed_bottom_navigation_container);
                            container.setLayoutParams(params);

                            container.setPadding(ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_12),
                                    ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_0),
                                    ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_12),
                                    ResourceUtils.dip2px(getApplicationContext(), R.dimen.dp_0));

                            //获取到Tab内的文字控件
                            TextView labelView = (TextView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
                            //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                            labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                            labelView.setIncludeFontPadding(true);

                            //获取到Tab内的图像控件
                            ImageView iconView = (ImageView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon);
                            params = new FrameLayout.LayoutParams(imgLen, imgLen);
                            params.gravity = Gravity.CENTER;
                            iconView.setLayoutParams(params);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeTab(int position) {
        try {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment hideFragment = fragmentList.get(bottomNavigationBar.getCurrentSelectedPosition());
            if (hideFragment.isAdded()) {
                ft.hide(hideFragment);
            }
            Fragment showFragment = fragmentList.get(position);
            if (showFragment.isAdded()) {
                ft.show(showFragment);
            } else {
                ft.add(R.id.bottom_nav_content, showFragment);
            }
            ft.commitAllowingStateLoss();
            bottomNavigationBar.selectTab(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showTab(boolean isShow) {
        bottomNavigationBar.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }
}