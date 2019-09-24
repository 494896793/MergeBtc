package com.bochat.app;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bochat.app.app.view.DebugView;
import com.bochat.app.business.fetcher.GroupMemberProvider;
import com.bochat.app.common.util.ALog;
import com.bochat.app.common.util.SharePreferenceUtil;
import com.bochat.app.common.util.ShareUtil;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupListEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.event.ReceiverMessageEvent;
import com.bochat.app.model.greendao.BoChatDbHelper;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.modelImpl.DynamicModel;
import com.bochat.app.model.modelImpl.GroupModule;
import com.bochat.app.model.modelImpl.UserModule;
import com.bochat.app.model.rong.BoChatMessage;
import com.bochat.app.model.rong.CustomizeMessageItemProvider;
import com.bochat.app.model.rong.ErrorTipsMessage;
import com.bochat.app.model.rong.ErrorTipsMessageItemProvider;
import com.bochat.app.model.rong.GetRedPacketMessage;
import com.bochat.app.model.rong.GroupRedPacketMessageItemProvider;
import com.bochat.app.model.rong.MyConnectionStatusListener;
import com.bochat.app.model.rong.MyExtensionModule;
import com.bochat.app.model.rong.MyRongMessageInterceptor;
import com.bochat.app.model.rong.RedPacketMessage;
import com.bochat.app.model.rong.RongConversationDetailListenner;
import com.bochat.app.model.rong.RongConversationListBehaviorListener;
import com.bochat.app.model.rong.RongCustomConversationTemplate;
import com.bochat.app.model.rong.RongReceiveMessageListener;
import com.bochat.app.model.rong.RongRedPacketMessageProvider;
import com.bochat.app.model.rong.RongSendMessageListener;
import com.bochat.app.model.rong.SpeedConverMessage;
import com.bochat.app.model.rong.SpeedConverMessageProvider;
import com.bochat.app.model.rong.SweetSystemMessage;
import com.bochat.app.model.util.Api;
import com.bochat.app.model.util.NotificationUtils;
import com.bochat.app.model.util.QuotationApi;
import com.bochat.app.mvp.injector.component.ApplicationComponent;
import com.bochat.app.mvp.injector.component.DaggerApplicationComponent;
import com.bochat.app.mvp.injector.module.ApplicationModule;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 09:50
 * Description :
 */

public class MainApplication extends Application {

    private static MainApplication application;
    private ApplicationComponent applicationComponent;

    private RongRedPacketMessageProvider redPacketMessageProvider;
    private MyExtensionModule myExtensionModule;
    private List<UserInfo> userInfoList;
    
    public MyExtensionModule getMyExtensionModule() {
        if (myExtensionModule == null) {
            myExtensionModule = new MyExtensionModule();
        }
        return myExtensionModule;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Log.i("=========", "=================application");
        try {
            userInfoList = new ArrayList<>();
            CrashHandler.getInstance().init(this);
            applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
            initDbManagerContext();
            NotificationUtils.getInstance().init(this);
            initRxJavaErrorHandler();
            initWeChat();
            initRong();
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(this));
            ARouter.init(this);
            ShareUtil.getInstance().registerQQ();
            ShareUtil.getInstance().registerWeiXin();
            DBManager.getInstance().findAllRedStatu();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    new DynamicModel().query();
//                }
//            }).start();
//
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static Context getContext() {
        return application;
    }

    public RongRedPacketMessageProvider getRedPacketProvider() {
        if (redPacketMessageProvider == null) {
            redPacketMessageProvider = new RongRedPacketMessageProvider();
        }
        return redPacketMessageProvider;
    }

    private void initRong() {
        RongIM.init(this);
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
        RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
        RongIM.getInstance().enableUnreadMessageIcon(true);//显示未读消息数目
        RongIM.setConversationClickListener(new RongConversationDetailListenner());
        RongIM.setConversationListBehaviorListener(new RongConversationListBehaviorListener());  //会话列表监听
        RongIM.getInstance().setSendMessageListener(RongSendMessageListener.getInstance());
        RongIM.setOnReceiveMessageListener(RongReceiveMessageListener.getInstance());
        RongIM.getInstance().setMessageInterceptor(new MyRongMessageInterceptor());
       /* RongIM.getInstance().setGroupMembersProvider(new RongIM.IGroupMembersProvider() {
            @Override
            public void getGroupMembers(String s, RongIM.IGroupMemberCallback callback) {
                userInfoList=initGroupMember(s);
                callback.onGetGroupMembersResult(userInfoList);

            }
        });*/
        //融云未读数监听
        RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
            @Override
            public void onCountChanged(int i) {
                ReceiverMessageEvent unReadEvent = new ReceiverMessageEvent();
                unReadEvent.isShowBadge = i > 0;
                EventBus.getDefault().post(unReadEvent);
            }
        }, Conversation.ConversationType.GROUP, Conversation.ConversationType.PRIVATE);
        try {
            RongIM.registerMessageType(SweetSystemMessage.class);

            RongIM.registerMessageType(BoChatMessage.class);
            RongIM.registerMessageTemplate(new CustomizeMessageItemProvider());

            RongIM.registerMessageType(RedPacketMessage.class); //注册名片消息
            RongIM.registerMessageTemplate(MainApplication.getInstance().getRedPacketProvider());

            RongIM.registerMessageType(GetRedPacketMessage.class);
            RongIM.registerMessageTemplate(new GroupRedPacketMessageItemProvider());

            RongIM.registerMessageType(SpeedConverMessage.class);
            RongIM.registerMessageTemplate(new SpeedConverMessageProvider());

            RongIM.registerMessageType(ErrorTipsMessage.class);
            RongIM.registerMessageTemplate(new ErrorTipsMessageItemProvider());

            //custom conversation template
            //自定义会话内容
            RongIM.getInstance().registerConversationTemplate(new RongCustomConversationTemplate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setMyExtensionModule();
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {
                FriendListEntity userEntity = new UserModule().getFriendInfo(userId, 1, 10);
                if (userEntity != null && userEntity.getItems() != null && userEntity.getItems().size() > 0) {
                    if (userEntity.getItems().get(0).getHead_img() == null) {
                        userEntity.getItems().get(0).setHead_img(Api.DEFAULT_HEAD);
                    }
                    return new UserInfo(userId, userEntity.getItems().get(0).getNickname(), Uri.parse(userEntity.getItems().get(0).getHead_img()));//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
                } else {
                    return null;
                }
            }
        }, true);
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String s) {
                ALog.d("get group info");
                GroupListEntity groupListEntity = new GroupModule().getGroupInfo(Long.valueOf(s), "", 1, 1);
                if (groupListEntity != null && groupListEntity.getItems() != null && groupListEntity.getItems().size() > 0) {
                    GroupEntity groupEntity = groupListEntity.getItems().get(0);
                    if(groupEntity != null && groupEntity.getRole() != 0){
                        String head = groupEntity.getGroup_head();
                        if (head == null) {
                            head = "";
                        }
                        return new Group(s, groupEntity.getGroup_name(), Uri.parse(head));
                    }
                }
                RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, s, new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                    }
                });
                return null;
            }
        }, true);
        
        initDebugConfig();
        
        Log.i("============", "===========create");
    }

    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(MainApplication.getInstance().getMyExtensionModule());
            }
        }
    }

    private void initDebugConfig() {
        List<DebugView.Config> configs = new ArrayList<>();
        configs.add(new DebugView.Config("api-app", Api.BASE_URL, 
                "http://" + BuildConfig.API_HOST + "/", new Api()));
        configs.add(new DebugView.Config("api-trade", QuotationApi.BASE_URL, 
                "http://" + BuildConfig.QAPI_HOST + "/", new QuotationApi(0)));
        configs.add(new DebugView.Config("api-trade-ws", QuotationApi.WS_SERVER_URI, 
                "ws://" + BuildConfig.QAPI_HOST + "/websocket", new QuotationApi(1)));
        DebugView.insertConfigs(configs);
    }
    
    private void initWeChat() {
    }

    public static MainApplication getInstance() {
        return application;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public void initDbManagerContext() {
        BoChatDbHelper.init(this);
        BoChatDbHelper.getInstance().initDatabase();
        DBManager.init(this);
        SharePreferenceUtil.init(this);
    }

    private void initRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ALog.d("RxJava something wrong ... " + throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }


    private List<UserInfo> initGroupMember(String groupId) {
        List<UserInfo> userInfos = new ArrayList<>();
        if (groupId != null){
            List<GroupMemberEntity> list = DBManager.getInstance().findGroupMembersByGroupId(Long.valueOf(groupId));
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMember_id() != 0){
                    userInfos.add(new UserInfo(list.get(i).getMember_id() + "", list.get(i).getNickname(), Uri.parse(list.get(i).getHead_img())));
                }
            }
        }

       /* GroupMemberProvider provider = new GroupMemberProvider(groupModule);
        List<GroupMemberEntity> entityList = provider.getGroupMemberList(Long.parseLong(groupId),false);
*/
        return userInfos;
    }
}