package com.bochat.app.model.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by ldl on 2019/4/9.
 */

public interface RetrofitService {
    // useing json type parameter
    @POST()
    Call<String> requestPost(@Url() String url, @Body RequestBody requestBody);

    @GET()
    Call<String> requestGet(@Url() String url, @QueryMap Map<String, Object> map);

    @PUT()
    Call<String> requestPut(@Url() String url, @QueryMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Call<String> sendCode(@Url() String url, @Field("phone") String phone, @Field("type") int type);

    @FormUrlEncoded
    @POST
    Call<String> codeLogin(@Url() String url, @Field("username") String username, @Field("code") String code, @Field("loginType") int loginType);

    @FormUrlEncoded
    @POST
    Call<String> pwdLogin(@Url() String url, @Field("username") String username, @Field("password") String password, @Field("loginType") int loginType);

    @FormUrlEncoded
    @POST
    Call<String> getInfo(@Url() String url, @Field("Authorization") String token, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> checkCode(@Url() String url, @Field("phone") String phone, @Field("type") String type, @Field("code") String code);

    @FormUrlEncoded
    @POST
    Call<String> updateUserInfo(@Url() String url, @Field("nickname") String nickname, @Field("sex") int sex, @Field("birthday") String age, @Field("area") String area, @Field("mySign") String mySign, @Field("headImg") String headImg);

    @FormUrlEncoded
    @POST
    Call<String> getFriendList(@Url() String url, @Field("Authorization") String token, @Field("userId") String userId, @Field("start") String start, @Field("offset") String offset);

    @FormUrlEncoded
    @POST
    Call<String> checkOnline(@Url() String url, @Field("String") String userId);

    @FormUrlEncoded
    @POST
    Call<String> getArea(@Url() String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> getFriendApplyList(@Url() String url, @Field("userId") String userId, @Field("start") int start, @Field("offset") int offset);

    @FormUrlEncoded
    @POST
    Call<String> createGroup(@Url() String url, @Field("userId") String userId, @Field("group_name") String group_name, @Field("introduce") String introduce, @Field("label") String label, @Field("head") String head);

    @FormUrlEncoded
    @POST
    Call<String> dealFriendApply(@Url() String url, @Field("apply_id") String apply_id, @Field("apply_state") String apply_state, @Field("refuse_text") String refuse_text);

    @FormUrlEncoded
    @POST
    Call<String> sendAddAFriendpply(@Url() String url, @Field("proposer_id") String proposer_id, @Field("target_id") String target_id, @Field("refuse_text") String refuse_text, @Field("apply_from") String apply_from);

    @FormUrlEncoded
    @POST
    Call<String> queryFriendList(@Url() String url, @Field("userId") String userId, @Field("start") String start, @Field("offset") String offset);

    @FormUrlEncoded
    @POST
    Call<String> getFriendInfo(@Url() String url, @Field("keyword") String userId, @Field("start") int start, @Field("offset") int offset);

    @FormUrlEncoded
    @POST
    Call<String> getFriendInfoExact(@Url() String url, @Field("keyword") String userId);

    @FormUrlEncoded
    @POST
    Call<String> updateChange(@Url() String url, @Field("head") String head, @Field("level") String level, @Field("introduce") String introduce, @Field("label") String label, @Field("target") String target, @Field("group_id") long group_id, @Field("group_name") String group_name, @Field("tradepwd") String tradepwd, @Field("price") double price);

    @FormUrlEncoded
    @POST
    Call<String> updateChange(@Url() String url, @Field("head") String head, @Field("introduce") String introduce, @Field("label") String label, @Field("group_id") long group_id, @Field("group_name") String group_name);

    @FormUrlEncoded
    @POST
    Call<String> addFriend(@Url() String url, @Field("targetId") String targetId);

    @FormUrlEncoded
    @POST
    Call<String> queryGroup(@Url() String url, @Field("keyword") String keyword, @Field("start") int start, @Field("offset") int offset);

    @FormUrlEncoded
    @POST
    Call<String> queryGroupExact(@Url() String url, @Field("group_id") long gapply_id, @Field("start") int start, @Field("offset") int offset);

    @FormUrlEncoded
    @POST
    Call<String> dissmissGroup(@Url() String url, @Field("group_id") long group_id, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> quitGroup(@Url() String url, @Field("groupId") long group_id);

    @FormUrlEncoded
    @POST
    Call<String> handleGroupApply(@Url() String url, @Field("applyId") String applyId, @Field("applyState") String applyState, @Field("refuseText") String refuseText);

    @FormUrlEncoded
    @POST
    Call<String> queryGroupLevel(@Url() String url, @Field("group_id") String group_id);

    @FormUrlEncoded
    @POST
    Call<String> getEnterGroupStatu(@Url() String url, @Field("userId") String userId, @Field("start") int start);

    @FormUrlEncoded
    @POST
    Call<String> getMyGroupApply(@Url() String url, @Field("userId") String userId, @Field("start") int start, @Field("offset") int offset);

    @FormUrlEncoded
    @POST
    Call<String> shieldFriendRelation(@Url String url, @Field("targetId") String targetId, @Field("type") String type, @Field("relation") String relation);

    @FormUrlEncoded
    @POST
    Call<String> blockFriendRelation(@Url String url, @Field("targetId") String targetId, @Field("type") String type, @Field("black") String black);

    @FormUrlEncoded
    @POST
    Call<String> unBlockFriendRelation(@Url String url, @Field("targetId") String targetId, @Field("type") String type);

    @FormUrlEncoded
    @POST
    Call<String> deleteFriendRelation(@Url String url, @Field("type") String type, @Field("targetId") String targetId);

    @FormUrlEncoded
    @POST
    Call<String> updateUserName(@Url() String url, @Field("userId") int userId, @Field("note") String note, @Field("int") int friendId);

    @FormUrlEncoded
    @POST
    Call<String> addGroupManager(@Url() String url, @Field("group_id") int group_id, @Field("groupToken") String groupToken, @Field("targetId") int targetId, @Field("targetToken") String targetToken);

    @FormUrlEncoded
    @POST
    Call<String> loginOut(@Url() String url, @Field("Authorization") String Authorization);

    @FormUrlEncoded
    @POST
    Call<String> setLoginPwd(@Url() String url, @Field("password") String password);

    @FormUrlEncoded
    @POST
    Call<String> queryMyGroupList(@Url() String url, @Field("userId") String userId, @Field("start") int start, @Field("offset") int offset);

    @FormUrlEncoded
    @POST
    Call<String> queryGroupMembers(@Url String url, @Field("group_id") long group_id, @Field("start") int start, @Field("offset") int offset);

    @FormUrlEncoded
    @POST
    Call<String> queryGroupApply(@Url String url, @Field("start") int start, @Field("offset") int offset);

    @FormUrlEncoded
    @POST
    Call<String> authentication(@Url String url, @Field("idCard") String idCard, @Field("name") String name);

    @FormUrlEncoded
    @POST
    Call<String> updateLogin(@Url String url, @Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST
    Call<String> forgetLogin(@Url String url, @Field("phone") String phone, @Field("code") String code, @Field("password") String password);

    @FormUrlEncoded
    @POST
    Call<String> getPassWord(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> authDetail(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> setTradePwd(@Url String url, @Field("password") String password, @Field("type") int type);

    @FormUrlEncoded
    @POST
    Call<String> updateTradePwd(@Url String url, @Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST
    Call<String> updatePhone(@Url String url, @Field("phone") String phone, @Field("code") String code);

    @FormUrlEncoded
    @POST
    Call<String> getBoChatLogo(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> joinGroup(@Url String url, @Field("group_id") String group_id, @Field("group_name") String group_name, @Field("answer") String answer);

    @FormUrlEncoded
    @POST
    Call<String> bindBank(@Url String url, @Field("bankId") int bankId, @Field("bankNo") String bankNo, @Field("bankBranch") String bankBranch);

    @FormUrlEncoded
    @POST
    Call<String> unbindUserBank(@Url String url, @Field("bankId") int bankId);

    @FormUrlEncoded
    @POST
    Call<String> getBank(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> getUserBank(@Url String url, @Field("type") int type);

    @FormUrlEncoded
    @POST
    Call<String> addGCTransfer(@Url String url, @Field("number") int number, @Field("password") String password, @Field("recipientUserId") int recipientUserId);

    @POST
    Call<String> getGCReceive(@Url String url);

    @FormUrlEncoded
    @POST
    Call<String> addTrunOut(@Url String url, @Field("trunoutnum") double trunoutnum, @Field("walletaddre") String walletaddre, @Field("tradpassw") String tradpassw, @Field("bid") int bid);

    @FormUrlEncoded
    @POST
    Call<String> getOutPrompt(@Url String url, @Field("bid") int bid);

    @FormUrlEncoded
    @POST
    Call<String> listPlatformCurrency(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> getUserCurrencyTrading(@Url String getUserCurrencyTrading, @Field("start") int start, @Field("offset") int offset, @Field("bid") int bid);

    @FormUrlEncoded
    @POST
    Call<String> getUserCurrencyDetails(@Url String url, @Field("bid") int bid);

    @POST
    Call<String> listUserCurrency(@Url String url);

    @POST
    Call<String> getUserCurrencyCny(@Url String url);

    @FormUrlEncoded
    @POST
    Call<String> getCandyDetails(@Url String url, @Field("id") int id);

    @FormUrlEncoded
    @POST
    Call<String> getChangeDetails(@Url String url, @Field("id") int id);

    @FormUrlEncoded
    @POST
    Call<String> getIncomeSpending(@Url String url, @Field("bid") int bid);

    @FormUrlEncoded
    @POST
    Call<String> tradingRecord(@Url String url, @Field("start") int start, @Field("offset") int offset, @Field("bid") int bid, @Field("orderType") int orderType);

    @FormUrlEncoded
    @POST
    Call<String> getAmount(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> userWithdraw(@Url String url, @Field("bankId") int bankId, @Field("money") String money, @Field("tradePwd") String tradePwd);

    @FormUrlEncoded
    @POST
    Call<String> getClasses(@Url String url, @Field("type") int type);

    @FormUrlEncoded
    @POST
    Call<String> userRecharge(@Url String url, @Field("payType") int payType, @Field("money") String money);

    @FormUrlEncoded
    @POST
    Call<String> getHandpick(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> speedConverList(@Url String url, @Field("startPage") int startPage, @Field("pageSize") String pageSize);

    @FormUrlEncoded
    @POST
    Call<String> mySpeedConver(@Url String url, @Field("startPage") int startPage, @Field("pageSize") String pageSize, @Field("type") int type, @Field("tradeStatus") int tradeStatus, @Field("tradeTime") String tradeTime);

    @FormUrlEncoded
    @POST
    Call<String> mySpeedConver(@Url String url, @Field("startPage") int startPage, @Field("pageSize") String pageSize, @Field("type") int type, @Field("tradeTime") String tradeTime);

    @FormUrlEncoded
    @POST
    Call<String> mySpeedConver(@Url String url, @Field("startPage") int startPage, @Field("pageSize") String pageSize, @Field("type") int type, @Field("tradeStatus") int tradeStatus);

    @FormUrlEncoded
    @POST
    Call<String> mySpeedConver(@Url String url, @Field("startPage") int startPage, @Field("pageSize") String pageSize, @Field("type") int type);

    @FormUrlEncoded
    @POST
    Call<String> myTradeDetail(@Url String url, @Field("id") int id, @Field("type") int type);

    @FormUrlEncoded
    @POST
    Call<String> sendSpeedConver(@Url String url, @Field("startId") int startId, @Field("converId") int converId, @Field("startNum") double startNum, @Field("converNum") double converNum, @Field("site") int site, @Field("isSync") int isSync, @Field("payPwd") String payPwd, @Field("relevanceId") long relevanceId);

    @FormUrlEncoded
    @POST
    Call<String> speedConverTrading(@Url String url, @Field("orderId") int orderId, @Field("payPwd") String payPwd);

    @FormUrlEncoded
    @POST
    Call<String> cancelConverTrading(@Url String url, @Field("orderId") int orderId);

    @FormUrlEncoded
    @POST
    Call<String> queryTakeRecord(@Url String url, @Field("rewardId") int rewardId, @Field("start") int start, @Field("offset") int offset);

    @FormUrlEncoded
    @POST
    Call<String> getWelfare(@Url String url, @Field("welfareId") int welfareId, @Field("userName") String userName);

    @FormUrlEncoded
    @POST
    Call<String> sendWelfareFirst(@Url String url, @Field("money") double money, @Field("count") int count, @Field("type") int type, @Field("text") String text, @Field("password") String password, @Field("groupId") String groupId, @Field("coin") int coin);

    @FormUrlEncoded
    @POST
    Call<String> sendWelfare(@Url String url, @Field("money") double money, @Field("count") int count, @Field("type") int type, @Field("text") String text, @Field("password") String password);

    @FormUrlEncoded
    @POST
    Call<String> sendWelfareFirstSecond(@Url String url, @Field("money") double money, @Field("count") int count, @Field("type") int type, @Field("text") String text, @Field("password") String password, @Field("coin") int coin);

    @FormUrlEncoded
    @POST
    Call<String> sendWelfareThirdRedHall(@Url String url, @Field("money") double money, @Field("count") int count, @Field("type") int type, @Field("text") String text, @Field("password") String password, @Field("groupId") String groupId,@Field("isSync") String isSync);

    @FormUrlEncoded
    @POST
    Call<String> sendWelfareFirstRedHall(@Url String url, @Field("money") double money, @Field("count") int count, @Field("type") int type, @Field("text") String text, @Field("password") String password, @Field("groupId") String groupId, @Field("coin") int coin,@Field("isSync") String isSync);

    @FormUrlEncoded
    @POST
    Call<String> sendWelfareRedHall(@Url String url, @Field("money") double money, @Field("count") int count, @Field("type") int type, @Field("text") String text, @Field("password") String password,@Field("isSync") String isSync);

    @FormUrlEncoded
    @POST
    Call<String> sendWelfareFirstSecondRedHall(@Url String url, @Field("money") double money, @Field("count") int count, @Field("type") int type, @Field("text") String text, @Field("password") String password, @Field("coin") int coin,@Field("isSync") String isSync);

    @FormUrlEncoded
    @POST
    Call<String> sendWelfareThird(@Url String url, @Field("money") double money, @Field("count") int count, @Field("type") int type, @Field("text") String text, @Field("password") String password, @Field("groupId") String groupId);

    @FormUrlEncoded
    @POST
    Call<String> listApplicationHome(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> getAppStoreType(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> listApplication(@Url String url, @Field("start") int start, @Field("offset") int offset, @Field("type") int type, @Field("classification") int typeId);

    @FormUrlEncoded
    @POST
    Call<String> listApplication(@Url String url, @Field("type") int type, @Field("isFeatured") int isFeatured);

    @FormUrlEncoded
    @POST
    Call<String> searchListApplication(@Url String url, @Field("type") String type, @Field("classification") String classification, @Field("name") String name, @Field("isHottest") int isHottest, @Field("id") int id, @Field("isFeatured") int isFeatured);

    @FormUrlEncoded
    @POST
    Call<String> searchListApplication(@Url String url, @Field("type") String type, @Field("name") String classification);

    @FormUrlEncoded
    @POST
    Call<String> searchListApplication(@Url String url, @Field("type") String type);

    @FormUrlEncoded
    @POST
    Call<String> searchListApplication(@Url String url, @Field("type") String type, @Field("isHottest") int isHottest);

    @FormUrlEncoded
    @POST
    Call<String> listBanner(@Url String url, @Field("type") int type);

    @POST("app/meet/listBanner")
    Call<String> listBanners(@Body MultipartBody multipartBody);

    @FormUrlEncoded
    @POST
    Call<String> listAnnouncement(@Url String url, @Field("start") int start, @Field("offset") int offset, @Field("type") int type);

    @FormUrlEncoded
    @POST
    Call<String> invitejoinGroup(@Url String url, @Field("group_id") String group_id, @Field("targetId") String targetId, @Field("userName") String userName);

    @FormUrlEncoded
    @POST
    Call<String> setGroupAuth(@Url String url, @Field("groupId") String group_id, @Field("joinType") int joinType, @Field("question") String question, @Field("answer") String answer);

    @POST
    Call<String> getUserInviteCode(@Url String url);

    @FormUrlEncoded
    @POST
    Call<String> getApplicationVersion(@Url String url, @Field("version") String version);

    @POST
    Call<String> getRecommendFriend(@Url String url);

    @POST
    Call<String> getRecommendGroup(@Url String url);

    @FormUrlEncoded
    @POST
    Call<String> getInfomationList(@Url String url, @Field("start") int start, @Field("offset") int offset, @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST
    Call<String> addProjectParty(@Url String url, @Field("companyName") String companyName, @Field("website") String website, @Field("logo") String logo, @Field("license") String license);

    @FormUrlEncoded
    @POST
    Call<String> getProjectParty(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> getProtocolBook(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> gameLogin(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> bitMallLogin(@Url String url, @Field("userId") String userId);

    @FormUrlEncoded
    @POST
    Call<String> getIncomeOfToday(@Url String url, @Field("currencyId") int currencyId);

    @POST
    Call<String> listAppService(@Url String url);


    @FormUrlEncoded
    @POST
    Call<String> getTokenRate(@Url String url, @Field("sourceToken") String sourceToken, @Field("targetToken") String targetToken);

    @FormUrlEncoded
    @POST
    Call<String> getExchangeAmount(@Url String url, @Field("sourceToken") String sourceToken, @Field("targetToken") String targetToken, @Field("sourceAmount") String sourceAmount);

    @POST
    Call<String> getExchangeToken(@Url String url);

    @FormUrlEncoded
    @POST
    Call<String> listActivities(@Url String url, @Field("start") String start, @Field("offset") String offset);

    @FormUrlEncoded
    @POST
    Call<String> doExchangeToken(@Url String url, @Field("currencyIdSource") String currencyIdSource,
                                 @Field("currencyIdTarget") String currencyIdTarget,
                                 @Field("currencyAmountSource") double currencyAmountSource,
                                 @Field("tradPass") String tradPass);

    @POST
    Call<String> queryUserBXInfo(@Url String url);

    @POST
    @FormUrlEncoded
    Call<String> incomeOfToday(@Url String url, @Field("currencyId") String Id);

    @POST
    Call<String> getPationInfo(@Url String url);


    @POST
    @FormUrlEncoded
    Call<String> muteAllMembers(@Url String url, @Field("groupId") String groupId, @Field("prohibitState") String prohibitState);

    @POST
    @FormUrlEncoded
    Call<String> queryMuteMembers(@Url String url, @Field("start") String start, @Field("offset") String offset, @Field("groupId") String groupId);

    @POST
    @FormUrlEncoded
    Call<String> muteMembers(@Url String url, @Field("groupId") String groupId, @Field("memberId") String memberId, @Field("prohibitState") String prohibitState);

    @POST
    @FormUrlEncoded
    Call<String> adminQuitGroup(@Url String url, @Field("groupId") long groupId, @Field("groupUserIdList") String groupUserIdList);

    @POST
    @FormUrlEncoded
    Call<String> addGroupManager(@Url String url, @Field("groupId") String groupId, @Field("targetId") String targetId);

    @POST
    @FormUrlEncoded
    Call<String> queryGroupManager(@Url String url, @Field("start") String start, @Field("offset") String offset, @Field("groupId") String groupId);

    @POST
    @FormUrlEncoded
    Call<String> deleteGroupManager(@Url String url, @Field("groupId") String groupId, @Field("userId") String userId);

    @POST
    @FormUrlEncoded
    Call<String> queryKLineRecord(@Url String url, @Field("marketId") long groupId, @Field("period") int period, @Field("startId") long startId, @Field("offset") int offset);

    @POST
    Call<String> getSweetsActivity(@Url String url);

    @POST
    Call<String> insertActivityRecord(@Url String url);
    
    @POST
    Call<String> getActivityRecord(@Url String url);

    @POST
    Call<String> getBannerForYysc(@Url String url);


    @POST
    @FormUrlEncoded
    Call<String> getGroupVerify(@Url String url, @Field("groupId") String groupId);

    @POST
    @FormUrlEncoded
    Call<String> queryApplyList(@Url String url, @Field("marketId") int marketId, @Field("type") int type, @Field("pageNum") int pageNum, @Field("pageSize") int pageSize);

    @POST
    Call<String> queryByCurrency(@Url String url);

    @POST
    @FormUrlEncoded
    Call<String> listResidueAmount(@Url String url, @Field("userId") long userId, @Field("currencyIdL") String currencyIdL, @Field("currencyIdR") String currencyIdR);

    @POST
    @FormUrlEncoded
    Call<String> isCollect(@Url String url, @Field("marketId") String marketId, @Field("isCollect") int isCollect);

    @POST
    @FormUrlEncoded
    Call<String> queryTradingRules(@Url String url, @Field("marketId") long marketId);

    @POST
    @FormUrlEncoded
    Call<String> saleBuying(@Url String url, @Field("marketId") long marketId, @Field("price") String price, @Field("num") double num, @Field("type") int type, @Field("currencyIdL") long currencyIdL, @Field("currencyIdR") long currencyIdR);

    @POST
    @FormUrlEncoded
    Call<String> queryCollection(@Url String url, @Field("marketId") long marketId);

    @POST
    @FormUrlEncoded
    Call<String> revoke(@Url String url, @Field("id") int id);

    @POST
    @FormUrlEncoded
    Call<String> messageLists(@Url String url, @Field("page") String page, @Field("pageSize") String pageSize);

    @POST
    @FormUrlEncoded
    Call<String> like(@Url String url, @Field("flashId") String flashId, @Field("option") String option);

    @POST
    Call<String> push(@Url String url);

    @POST
    Call<String> findDownloadPath(@Url String url);

    @POST
    @FormUrlEncoded
    Call<String> getRewardHallList(@Url String url,@Field("startPage") String startPage,@Field("pageSize") String pageSize);

    @POST
    @Multipart
    Call<String> listPrivileges(@Url String url, @Part("currentPage") int pageNum, @Part("pageSize") int pageSize);

    @POST
    Call<String> getOpenVip(@Url String url);

    @POST
    Call<String> query(@Url String url);

    @POST
    @Multipart
    Call<String> payVip(@Url String url, @Part("id") String id, @Part("tradePass") String tradePass);

    @POST
    @Multipart
    Call<String> getRewardReceiveDetails(@Url String url,@Part("startPage") String startPage,@Part("pageSize") String pageSize,@Part("rewardId") String rewardId);


    @POST
    @Multipart
    Call<String> queryTeamMembers(@Url String url,@Part("currentPage") String currentPage,@Part("pageSize") String pageSize);


    @POST
    Call<String> getAdvertPageList(@Url String url);
    //    // 登录接口
    //    // POST /login
    //    @POST("login")
    //    @Headers({"Content-Type: application/json", "Accept: application/json"})
    //    Flowable<String> userLogin(@Body RequestBody requestBody);
}
