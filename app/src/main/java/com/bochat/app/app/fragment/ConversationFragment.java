package com.bochat.app.app.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.GoodsRedDialog;
import com.bochat.app.app.view.MarqueeView;
import com.bochat.app.app.view.PlusMorePopupWindow;
import com.bochat.app.app.view.UPMarqueeView;
import com.bochat.app.common.contract.book.AddressBookContract;
import com.bochat.app.common.contract.conversation.ConversationContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicNoticeFlash;
import com.bochat.app.common.router.RouterQuickExchange;
import com.bochat.app.common.router.RouterSearchGroup;
import com.bochat.app.model.bean.DynamicPlushEntity;
import com.bochat.app.model.bean.ShakyCandyEntity;
import com.bochat.app.model.bean.ShakyStatuEntity;
import com.bochat.app.model.bean.SweetSystemEntity;
import com.bochat.app.model.bean.SweetSystemSerializable;
import com.bochat.app.model.event.ConversationDetailRefreshEvent;
import com.bochat.app.model.event.ConversationRefreshEvent;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bochat.app.mvp.view.BaseFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

import static com.bochat.app.model.constant.Constan.GET_SWEET_RECEIVER;
import static com.bochat.app.model.constant.Constan.SWEET_SYSTEM_MESSAGE;

/**
 * Author      : LDL
 * CreateDate  : 2019/04/12 09:30
 * Description :
 */

public class ConversationFragment extends BaseFragment<ConversationContract.Presenter> implements ConversationContract.View, OnRefreshListener {

    @Inject
    ConversationContract.Presenter presenter;
    @Inject
    AddressBookContract.Presenter bookPresenter;

    @BindView(R.id.bochat_topbar)
    BoChatTopBar boChatTopBar;

    @BindView(R.id.notice_linear)
    LinearLayout notice_linear;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.candy_img)
    ImageView candy_img;

    @BindView(R.id.upmarquee_view)
    UPMarqueeView upmarquee_view;

    @BindView(R.id.marqueeview)
    MarqueeView marqueeview;

    @BindView(R.id.fast_news_close)
    ImageView fast_news_close;

    @BindView(R.id.fast_news_linear)
    LinearLayout fast_news_linear;

    private ObjectAnimator objectAnimator;

    PlusMorePopupWindow plusMorePopupWindow;
    
    private ConversationListFragment fragment;
    private Handler handler = new Handler();
    private GoodsRedDialog goodsRedDialog;
    private List<SweetSystemSerializable> sweetContentList=new ArrayList<>();
    private ShakyStatuEntity entity;
    
    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected ConversationContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        registerSweetReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initDialog() {
        goodsRedDialog = new GoodsRedDialog(getActivity());
        goodsRedDialog.setCancelable(false);
        goodsRedDialog.show();
        goodsRedDialog.dismiss();
    }

    @Subscribe
    public void onEventMainThread(ConversationRefreshEvent event) {
        if (fragment != null) {
            fragment.onRestoreUI();
        }
    }

    @Subscribe
    public void onEventMainThread(ConversationDetailRefreshEvent event) {
        if (fragment != null && event.getType() == 1) {
            fragment.onRestoreUI();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        loadConversations();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA,}, 1);
        }
    }

    @Override
    protected void initWidget() {
        initDialog();
        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onLeftExtButtonClick() {
//                presenter.onQRScanClick();
            }

            @Override
            public void onRightExtButtonClick() {
                showPlusMorePopupWindow(boChatTopBar.getExtBtn2());
            }

            @Override
            public void onTextExtButtonClick() {

            }
        });

        mRefreshLayout.setOnRefreshListener(this);
        initNoticeLinear();
    }

    private void initNoticeLinear() {
        notice_linear.getBackground().setAlpha(180);
        initAnim();
//        for (int i = 0; i < sweetNotices.size(); i++) {
        if(sweetContentList!=null){
            for (int i = 0; i < sweetContentList.size(); i++) {
                addNoticeView(sweetContentList.get(i));
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initNoticeViewVisible();
            }
        },2000);
    }

    @OnClick({R.id.bochat_search_layout, R.id.candy_img,R.id.fast_news_close,R.id.fast_news_linear})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.fast_news_close:
                initVisibleAnima(fast_news_linear,false);
                fast_news_linear.setVisibility(View.GONE);
                break;
            case R.id.fast_news_linear:
                Router.navigation(new RouterDynamicNoticeFlash());
                break;

            case R.id.candy_img:
                if(entity.getType().equals("0")){
                    showTips("活动未开始");
                }else if(entity.getType().equals("2")){
                    showTips("活动已结束");
                }else{
                    presenter.getActivityRecord();
                }
                break;
            case R.id.bochat_search_layout:
                presenter.onSearchHistoryClick();
                break;
        }
    }

    private void showPlusMorePopupWindow(View view) {
        if (plusMorePopupWindow == null) {

            ArrayList<PlusMorePopupWindow.Item> menus = new ArrayList<>();

            menus.add(new PlusMorePopupWindow.Item("添加好友", R.mipmap.home_more_increase));
            menus.add(new PlusMorePopupWindow.Item("创建群聊", R.mipmap.home_more_establish));
            menus.add(new PlusMorePopupWindow.Item("查找群聊", R.mipmap.home_more_lookup));
            menus.add(new PlusMorePopupWindow.Item("发起闪兑", R.mipmap.home_more_exchange));
            menus.add(new PlusMorePopupWindow.Item("扫一扫", R.mipmap.home_more_scan));

            plusMorePopupWindow = new PlusMorePopupWindow(getContext(), menus);
            int[] mLocation = new int[2];
            view.getLocationInWindow(mLocation);
            plusMorePopupWindow.setOffsetX(view.getWidth() + (view.getWidth() / 2));

            plusMorePopupWindow.setOnPopupWindowItemClickListener(new PlusMorePopupWindow.OnPopupWindowItemClickListener() {
                @Override
                public void onPopupWindowItemClick(PlusMorePopupWindow.Item item, int position) {
                    switch (item.getIcon()) {
                        case R.mipmap.home_more_increase:
                            presenter.onSearchFriendBtnClick();
                            break;
                        case R.mipmap.home_more_establish:
                            presenter.onCreateGroupBtnClick();
                            break;
                        case R.mipmap.home_more_lookup:
                            Router.navigation(new RouterSearchGroup());
                            break;
                        case R.mipmap.home_more_exchange:
                            Router.navigation(new RouterQuickExchange());
                            break;
                        case R.mipmap.home_more_scan:
                            presenter.onQRScanClick();
                            break;
                    }

                }
            });

        }
        if (!plusMorePopupWindow.isShowing()) {
            plusMorePopupWindow.showPopupWindow(view);
        }
    }

    private void initAnim() {
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotation", 0, 35, 0, -35, 0, 35, 0, -35, 0);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.1f, 1.0f);
        PropertyValuesHolder scaley = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.17f, 1.0f);
        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(candy_img, scaleX, scaley, rotation);
        objectAnimator.setDuration(900);
        handler.post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startAnimation();
            handler.postDelayed(runnable, 3500);
        }
    };

    private void startAnimation() {
        objectAnimator.start();
    }

    /**
     * 加载 会话列表 ConversationListFragment
     */
    private void loadConversations() {
        fragment = new RongCustomConversationListFragment();
//        String url="rong://com.bochat.app/conversation/group?targetId=12345";
        Uri uri = Uri.parse("rong://" + "com.bochat.app").buildUpon()
                .appendPath("conversation")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
//                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.conversationlist, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        fragment.onRestoreUI();
        refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
    }

    @Override
    public void getSweetsSuccess(ShakyStatuEntity entity) {
        this.entity=entity;
        if (entity.getIsStart().equals("0")) {
            candy_img.setVisibility(View.VISIBLE);
            if(entity.getType().equals("1")){
                initNoticeViewVisible();
            }else{
                hideMessageScroll();
            }
        } else {
            hideMessageScroll();
        }
    }

    private void hideMessageScroll(){
        candy_img.setVisibility(View.GONE);
        initVisibleAnima(notice_linear,false);
        initVisibleAnima(upmarquee_view,false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notice_linear.setVisibility(View.GONE);
            }
        },900);
    }

    @Override
    public void getSweetsFailed() {
        candy_img.setVisibility(View.GONE);
    }

    @Override
    public void insertActivitySuccess(ShakyCandyEntity candyEntity) {
        if (goodsRedDialog != null) {
            goodsRedDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    candy_img.setVisibility(View.GONE);
                }
            });
            goodsRedDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    candy_img.setVisibility(View.VISIBLE);
                }
            });
            goodsRedDialog.setEntity(candyEntity);
            goodsRedDialog.show();
        }
    }

    @Override
    public void updateNoticePush(DynamicPlushEntity dynamicPlushEntity) {
        if(dynamicPlushEntity==null||dynamicPlushEntity.getFlash()==null||(TextUtils.isEmpty(dynamicPlushEntity.getFlash().getContent())&&TextUtils.isEmpty(dynamicPlushEntity.getFlash().getTitle()))){
            fast_news_linear.setVisibility(View.GONE);
        }else {
            if(marqueeview.getChildCount()==0){
                TextView textView = new TextView(getActivity());
                textView.setText(dynamicPlushEntity.getFlash().getTitle()+": "+dynamicPlushEntity.getFlash().getContent());
                textView.setTextColor(getResources().getColor(R.color.color_222222));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,ResourceUtils.dip2px(getActivity(),R.dimen.dp_12));
                marqueeview.addViewInQueue(textView);
                marqueeview.setScrollSpeed(4);
                marqueeview.setScrollDirection(MarqueeView.RIGHT_TO_LEFT);
                marqueeview.setViewMargin(15);
                marqueeview.startScroll();
                initVisibleAnima(fast_news_linear,true);
                fast_news_linear.setVisibility(View.VISIBLE);
            }
        }
    }

    private void registerSweetReceiver(){
        SweetReceiver sweetReceiver=new SweetReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(GET_SWEET_RECEIVER);
        getActivity().registerReceiver(sweetReceiver,intentFilter);
    }

    class SweetReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(GET_SWEET_RECEIVER)){
                    SweetSystemEntity sweetContent= (SweetSystemEntity) intent.getSerializableExtra(SWEET_SYSTEM_MESSAGE);
                    if(sweetContent.getList().size()>50){
                        sweetContentList.addAll(sweetContent.getList().subList(0,50));
                    }else {
                        sweetContentList.addAll(sweetContent.getList());
                    }
                for(int i=0;i<sweetContent.getList().size();i++){
                    addNoticeView(sweetContent.getList().get(i));
                }

            }
        }
    }

    private void addNoticeView(final SweetSystemSerializable sweetContent){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view=LayoutInflater.from(getActivity()).inflate(R.layout.horse_race_lamp, null);
                TextView user_name=view.findViewById(R.id.user_name);
                TextView money_text=view.findViewById(R.id.money_text);
                TextView money_label=view.findViewById(R.id.money_label);
                ImageView user_img=view.findViewById(R.id.user_img);
                if(!TextUtils.isEmpty(sweetContent.getUserName())){
                    if(sweetContent.getUserName().length()>=1){
                        user_name.setText(sweetContent.getUserName().substring(0,1)+"**：");
                    }else{
                        user_name.setText("**：");
                    }
                }else{
                    user_name.setText("**：");
                }
                money_text.setText(sweetContent.getNum());
                money_label.setText(sweetContent.getCurrency());
                Glide.with(getActivity()).load(sweetContent.getHead()).transform(new CenterCrop(),new GlideRoundTransform(getActivity(),10)).into(user_img);
                upmarquee_view.addViews(view);
                initNoticeViewVisible();
            }
        });
    }

    private void initNoticeViewVisible(){
        if(entity!=null&&entity.getIsStart().equals("0")&&entity.getType().equals("1")){
            if(upmarquee_view.getChildCount()>0){
                if(notice_linear.getVisibility()==View.GONE){
                    upmarquee_view.setVisibility(View.VISIBLE);
                    notice_linear.setVisibility(View.VISIBLE);
                    initVisibleAnima(notice_linear,true);
                    initVisibleAnima(upmarquee_view,true);
                }
            }
        }else{
            hideMessageScroll();
        }
    }

    private void initVisibleAnima(View view,boolean isVisible){
        ObjectAnimator objectAnimator=null;
        if(isVisible){
            objectAnimator=ObjectAnimator.ofFloat(view,"scaleX",0,1);
        }else{
            objectAnimator=ObjectAnimator.ofFloat(view,"scaleX",1,0);
        }
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

}
