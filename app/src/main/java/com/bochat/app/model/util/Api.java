package com.bochat.app.model.util;

import com.bochat.app.BuildConfig;
import com.bochat.app.app.view.DebugView;
import com.bochat.app.model.net.HttpClient;

/**
 * Created by cookie on 2017/5/22 15:13.
 *
 * @author LDL
 */
//All request API
public class Api implements DebugView.DebugValueProvider {

    public static String BASE_URL = "http://" + BuildConfig.API_HOST + "/";

//    public static final String BASE_URL = "http://api-app.bochat.net/"; //生产环境
    //public static final String BASE_URL="http://192.168.0.138:8585/";    //吕慧婷
//    public static final String BASE_URL = "http://192.168.0.100:8585/";//王磊
//    public static final String BASE_URL="http://192.168.0.158:8585/";    //范广金
//            public static final String BASE_URL = "http://47.244.177.24:8585/";//测试
//    public static final String BASE_URL="http://192.168.0.192:8585/";     //温梓宏
//    public static final String BASE_URL = "http://192.168.0.57:8585/";//徐昶
//    public static final String BASE_URL = "http://192.168.0.50:8585/";//刘泽萱
//        public static final String BASE_URL = "http://192.168.0.173:8585/";//杨贵华
//        public static final String   BASE_URL = "http://192.168.0.233:8585/";//黄世文
//        public static final String   BASE_URL = "http://192.168.0.108:8585/";//李达荣
//    public static final String BASE_URL = "http://192.168.0.30:8585/";//曾文
//        public static final String   BASE_URL = "http://192.168.0.224:8585/";//熊定坤
//        public static final String   BASE_URL = "http://192.168.0.188:8585/";//魏直雄
//        public static final String   BASE_URL = "http://192.168.0.156:8585/";//范广金
//    public static final String BASE_URL = "http://192.168.0.22:8585/";//曾文
    //   默认头像
    public static final String DEFAULT_HEAD = "http://bochatoss.oss-cn-beijing.aliyuncs.com/bochatapp/headImg/headImg.png";

    /*登录*/
    /*登出*/
    public static final String LOGIN_URL = "login";
    public static final String LOGIN_OUT = "app/user/logout";
    /*获取短信验证码*/
    public static final String GET_CODE = "app/user/getCode";
    /*短信验证*/
    public static final String CHECK_CODE = "app/user/checkCode";
    /*查询个人信息*/
    public static final String GET_USER_INFO = "app/user/getUserInfo";
    /*获取地区列表*/
    public static final String GET_AREA = "app/user/getArea";
    /*查询好友列表*/
    public static final String GET_FRIEND_LIST_FINO = "app/im/queryFriendList";
    /*检查用户在线状态*/
    public static final String checkOnline = "app/im/checkOnline";
    /*查询好友申请列表/ 查询我申请的列表*/
    public static final String getFriendApplyList = "app/im/queryApplyList";
    /*创建群*/
    public static final String createGroup = "app/im/createGroup";
    /*处理好友申请*/
    public static final String dealFriendApply = "app/im/handleTheApply";
    /*发送添加申请*/
    public static final String sendAddAFriendpply = "app/im/addFriendApply";
    /*添加好友*/
    public static final String addFriend = "app/im/addFriend";
    /*处理进群许可*/
    public static final String handleGroupApply = "app/im/handleGroupApply";
    /*查询好友信息*/
    public static final String getFriendInfo = "app/im/getUserInfo";
    /*修改群信息*/
    public static final String updateChange = "app/im/updateGroup";
    /*查询群组信息*/
    public static final String getUpdateChange = "app/im/queryGroup";
    /*解散群*/
    public static final String dissmissGroup = "app/im/dismissGroup";
    /*退出群*/
    public static final String quitGroup = "app/im/quitGroup";
    /*获取群聊等级及升级费用*/
    public static final String queryGroupLevel = "app/im/queryGroupLevel";
    /*群主查看进群申请*/
    public static final String getEnterGroupStatu = "app/im/queryGroupApply";
    /*查看我的进群申请*/
    public static final String getMyGroupApply = "app/im/queryMyGroupApply";
    /*修改好友备注*/
    public static final String updateUserName = "app/im/updateFriendNote";
    /*添加群管理员*/
    public static final String addGroupManager = "app/im/addGroupManager";
    /*邀请加入群聊*/
    public static final String invitejoinGroup = "app/im/inviteGroup";
    /*群权限设置*/
    public static final String setGroupAuth = "/app/im/setGroupAuth";
    /*加入群聊*/
    public static final String joinGroup = "app/im/joinGroup";
    /*编辑个人信息*/
    public static final String updateUserInfo = "app/user/updateUserInfo";
    /*获取邀请码*/
    public static final String getUserInviteCode = "/app/meet/getUserInviteCode";
    /*群聊列表*/
    public static final String queryMyGroupList = "app/im/queryMyGroupList";
    /*好友关系管理*/
    public static final String updateFriendRelation = "app/im/updateFriendRelation";
    /*设置登录密码*/
    public static final String setLoginPwd = "app/my/setLoginPwd";
    /*修改登录密码*/
    public static final String updateLogin = "app/my/updateLogin";
    /*忘记登录密码*/
    public static final String forgetLogin = "app/my/forgetLoginPwd";
    /*查询是否设置密码*/
    public static final String getPassWord = "app/my/getPassWord";
    /*查询群成员*/
    public static final String queryGroupMembers = "app/im/queryGroupMembers";
    /*查询群申请列表*/
    public static final String queryGroupApply = "app/im/queryGroupApply";
    /*实名认证*/
    public static final String authentication = "app/my/authentication";
    /*实名认证详情*/
    public static final String authDetail = "app/my/authDetail";
    /*设置支付密码*/
    public static final String setTradePwd = "app/my/setTradePwd";
    /*修改支付密码*/
    public static final String updateTradePwd = "app/my/updateTradePwd";
    /*忘记登录密码*/
    public static final String forgetLoginPwd = "app/my/forgetLoginPwd";
    /*更换手机号*/
    public static final String updatePhone = "app/my/updatePhone";
    /*关于我们-LOGO*/
    public static final String getBoChatLogo = "app/user/getBoChatLogo";
    /*获取银行卡列表*/
    public static final String getBank = "app/asset/getBank";
    /*绑定银行卡*/
    public static final String bindBank = "app/asset/bindBank";
    /*解绑银行卡*/
    public static final String unbindUserBank = "app/asset/unbindUserBank";
    /*查询用户银行卡*/
    public static final String getUserBank = "app/asset/getUserBank";

    /*资产-GC划转*/
    public static final String addGCTransfer = "app/trading/addGCTransfer";
    /*Token资产-GC专用码*/
    public static final String getGCReceive = "app/trading/getGCReceive";
    /*资产-资产转出*/
    public static final String addTrunOut = "app/trading/addTrunOut";
    /*资产-资产转出提示信息*/
    public static final String getOutPrompt = "app/trading/getOutPrompt";
    /*资产-查询所有币种列表(选择令牌、接收选择资产)*/
    public static final String listPlatformCurrency = "app/trading/listPlatformCurrency";
    /*资产-查询币划转、发送交易记录*/
    public static final String getUserCurrencyTrading = "app/trading/getUserCurrencyTrading";
    /*资产-查询币详情、接收*/
    public static final String getUserCurrencyDetails = "app/trading/getUserCurrencyDetails";
    /*资产-查询所有币种列表*/
    public static final String listUserCurrency = "app/trading/listUserCurrency";
    /*资产-查询币总额*/
    public static final String getUserCurrencyCny = "app/trading/getUserCurrencyCny";
    /*资产-糖果盒交易记录详情*/
    public static final String getCandyDetails = "app/asset/getCandyDetails";
    /*资产-账单收入支出总额*/
    public static final String getIncomeSpending = "app/asset/getIncomeSpending";
    /*资产-账单交易记录(零钱罐、糖果盒)*/
    public static final String tradingRecord = "app/asset/tradingRecord";
    /*资产-零钱罐交易记录详情*/
    public static final String getChangeDetails = "app/asset/getChangeDetails";
    /*零钱罐-零钱余额*/
    public static final String getAmount = "app/asset/getAmount";
    /*零钱罐-提现*/
    public static final String userWithdraw = "app/asset/userWithdraw";
    /*零钱罐-专区分类*/
    public static final String getClasses = "app/asset/getClasses";
    /*零钱罐-零钱充值*/
    public static final String userRecharge = "app/asset/userRecharge";
    /*零钱罐-获取精选商品列表*/
    public static final String getHandpick = "app/asset/getHandpick";
    /*闪兑大厅列表*/
    public static final String speedConverList = "app/speedConver/speedConverList";
    /*我的订单*/
    public static final String mySpeedConver = "app/speedConver/mySpeedConver";
    /*我的订单-交易详情*/
    public static final String myTradeDetail = "app/speedConver/myTradeDetail";
    /*发起闪兑*/
    public static final String sendSpeedConver = "app/speedConver/sendSpeedConver";
    /*闪兑兑换交易*/
    public static final String speedConverTrading = "app/speedConver/speedConverTrading";
    /*闪兑取消交易*/
    public static final String cancelConverTrading = "app/speedConver/cancelConverTrading";
    /*发红包*/
    public static final String sendWelfare = "app/welfare/sendWelfare";
    /*抢红包*/
    public static final String getWelfare = "app/welfare/getWelfare";
    /*查询红包领取记录*/
    public static final String queryTakeRecord = "app/welfare/queryTakeRecord";
    /*查询应用商店前四条-首页*/
    public static final String listApplicationHome = "app/meet/listApplicationHome";
    /*查询应用商店类型*/
    public static final String getAppStoreType = "app/meet/getAppStoreType";
    /*查询应用商店、推荐游戏列表*/
    public static final String listApplication = "app/meet/listApplication";
    /*查询banner列表*/
    public static final String listBanner = "app/meet/listBanner";
    /*查询公告列表*/
    public static final String listAnnouncement = "app/meet/listAnnouncement";
    /*更新版本*/
    /*版本更新*/
    public static final String getVersion = "app/user/getVersion";
    /*随机推荐十位好友*/
    public static final String recommendFriend = "app/im/recommendFriend";
    /*随机推荐十个群聊*/
    public static final String recommendGroup = "/app/im/recommendGroup";
    /*今日看点列表*/
    public static final String getInfomationList = "app/info/getInfomationList";
    /*添加项目方合作申请*/
    public static final String addProjectParty = "app/meet/addProjectParty";
    /*项目方认证状态*/
    public static final String getProjectParty = "app/meet/getProjectParty";
    /*BoChat项目合作协议书*/
    public static final String getProtocolBook = "app/meet/getProtocolBook";
    /*启动登录游戏*/
    public static final String gameLogin = "app/meet/gameLogin";
    /*启动登录BitMall*/
    public static final String bitMallLogin = "app/meet/bitmallLogin";
    /*获取今日收益 测试*/
    public static final String getIncomeOfToday = "app/my/incomeOfToday";
    /*获取汇率*/
    public static final String getTokenRate = "app/flashExchange/getTokenRate";
    /*获取兑换金额*/
    public static final String getExchangeAmount = "app/flashExchange/getExchangeAmount";
    /*获取可闪兑代币列表*/
    public static final String getExchangeToken = "app/flashExchange/getExchangeToken";
    /*进行闪兑*/
    public static final String doExchangeToken = "app/flashExchange/doExchangeToken";
    /*查询第三方服务列表*/
    public static final String listAppService = "app/meet/listAppService";
    /*查询活动中心*/
    public static final String listActivitys = "app/meet/listActivitys";

    /*活动页面查询用户金币状态*/
    public static final String queryUserBXInfo = "app/my/queryUserBXInfo";
    /*活动页面收益*/
    public static final String incomeOfToday = "app/my/incomeOfToday";

    /*全体禁言（或取消）*/
    public static final String muteAllMembers = "app/im/MuteAllMembers";

    /*查询被禁言列表*/
    public static final String queryMuteMembers = "app/im/queryMuteMembers";

    /*解除当成员禁言状态*/
    public static final String muteMembers = "app/im/muteMembers";

    /*管理员踢出群成员*/
    public static final String adminQuitGroup = "app/im/adminQuitGroup";
    /*查询管理员列表*/
    public static final String queryGroupManager = "app/im/queryGroupManager";
    /*删除管理员*/
    public static final String deleteGroupManager = "app/im/deleteGroupManager";
    /*获取交易区块*/
    public static final String getPationInfo = "app/marketInfo/getPationInfo";
    /*判断领糖果活动是否开启*/
    public static final String getSweetsActivity = "activity/getSweetsActivity";
    /*领取糖果接口*/
    public static final String insertActivityRecord = "activity/insertActivityRecord";
    /*查询领取糖果记录*/
    public static final String getActivityRecord = "activity/getActivityRecord";
    /*查询应用市场轮播图*/
    public static final String getBannerForYysc = "app/meet/getBannerForYysc";

    /*获取问题和答案*/
    public static final String getGroupVerify = "app/im/getGroupVerify";

    /*获取动态快讯列表*/
    public static final String messageLists = "app/message/messageLists";

    /*发送点赞请求*/
    public static final String like = "app/message/like";
    /*快讯分享详情*/
    public static final String push = "app/message/push";
    /*快讯二维码*/
    public static final String findDownloadPath = "app/message/findDownloadPath";
    /*获取是否开通Vip*/
    public static final String getOpenVip="app/rewardhall/getOpenVip";
    /*获取红包列表*/
    public static final String getRewardHallList="app/rewardhall/getRewardHallList";
    /*获取领取详情*/
    public static final String getRewardReceiveDetails="app/rewardhall/getRewardReceiveDetails";
    /*查询Vip价格列表*/
    public static final String query="vip/VipPrice/query";
    /*Vip购买*/
    public static final String payVip="vip/VipRecord/pay";

    /*获取通讯录团队列表*/
    public static final String queryTeamMembers = "app/im/queryTeamMembers";

    /**
     * 获取启动广告页列表
     */
    public static final String getAdvertPageList = "app/advert/getAdvertPageList";
    @Override
    public String getValue() {
        return BASE_URL;
    }

    @Override
    public void setValue(String value) {
        BASE_URL = value;
        HttpClient.release();
    }
}
