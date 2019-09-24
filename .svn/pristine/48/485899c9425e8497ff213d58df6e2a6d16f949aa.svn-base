package com.bochat.app.business.cache;

import com.bochat.app.model.bean.UserEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/07 09:49
 * Description :
 */

public class CachePool {
    
    private static CachePool instance;
    
    private CacheUserEntity userCache;
    private CacheLoginUserEntity loginUserCache;
    private CacheFriendEntity friendCache;
    private CacheTeamEntity teamCache;
    private CacheGroupEntity groupCache;
    private CacheFriendEntity friendDetailCache;
    private CacheGroupEntity groupDetailCache;
    private CacheDynamicPlushEntity dynamicPlushEntity;
    
    private CacheFriendApplyEntity friendApplyCache;
    private CacheGroupApplyEntity groupApplyCache;
    private CacheRealNameAuthEntity realNameAuthCache;
    private CacheBankCardEntity bankCardCache;

    private static final int USER_CACHE_TYPE = BaseCache.TYPE_A_SIMPLE_CACHE;
    private static final int LOGIN_USER_CACHE_TYPE = BaseCache.TYPE_A_SIMPLE_CACHE;
    
    private static final int FRIEND_CACHE_TYPE = BaseCache.TYPE_DB_FRIEND;
    private static final int TEAM_CACHE_TYPE = BaseCache.TYPE_DB_TEME;
    private static final int GROUP_CACHE_TYPE = BaseCache.TYPE_DB_GROUP;
    private static final int FRIEND_APPLY_CACHE_TYPE = BaseCache.TYPE_DB_FRIEND_APPLY;
    private static final int GROUP_APPLY_CACHE_TYPE = BaseCache.TYPE_DB_GROUP_APPLY;

    private static final int FRIEND_DETAIL_CACHE_TYPE = BaseCache.TYPE_SPARSE_ARRAY;
    private static final int GROUP_DETAIL_CACHE_TYPE = BaseCache.TYPE_SPARSE_ARRAY;
    private static final int REAL_NAME_AUTH_CACHE_TYPE = BaseCache.TYPE_SPARSE_ARRAY;
    private static final int BANK_CARD_CACHE_TYPE = BaseCache.TYPE_SPARSE_ARRAY;

    private CachePool(){
    }
    
    public static CachePool getInstance(){
        if(instance == null){
            instance = new CachePool();
        }
        return instance;
    }
    
    public void destroy(){
        friend().clear();
        group().clear();
        loginUser().clear();
        user().clear();
        friendCache = null;
        friendDetailCache = null;
        friendApplyCache = null;
        groupCache = null;
        groupDetailCache = null;
        groupApplyCache = null;
        realNameAuthCache = null;
        bankCardCache = null;
    }
    
    public CacheUserEntity user(){
        if(userCache == null){
            userCache = new CacheUserEntity("userCache", USER_CACHE_TYPE);
        }
        return userCache;
    }
    public CacheLoginUserEntity loginUser(){
        if(loginUserCache == null){
            loginUserCache = new CacheLoginUserEntity("loginUserCache", LOGIN_USER_CACHE_TYPE);
        }
        return loginUserCache;
    }
    
    public CacheFriendEntity friend(){
        if(friendCache == null){
            UserEntity latest = user().getLatest();
            String id = latest == null ? "0" : latest.getAccount();
            friendCache = new CacheFriendEntity("friendCache" + id, FRIEND_CACHE_TYPE);
        }
        return friendCache;
    }

    public CacheFriendEntity friendDetail(){
        if(friendDetailCache == null){
            friendDetailCache = new CacheFriendEntity("friendDetailCache", FRIEND_DETAIL_CACHE_TYPE);
        }
        return friendDetailCache;
    }

    public CacheTeamEntity team(){
        if (teamCache == null){
            UserEntity latest = user().getLatest();
            String id = latest == null ? "0" : latest.getAccount();
            teamCache = new CacheTeamEntity("teamCache"+id, TEAM_CACHE_TYPE);
        }
        return teamCache;
    }

    
    public CacheGroupEntity group(){
        if(groupCache == null){
            UserEntity latest = user().getLatest();
            String id = latest == null ? "0" : latest.getAccount();
            groupCache = new CacheGroupEntity("groupCache" + id, GROUP_CACHE_TYPE);
        }
        return groupCache;
    }

    public CacheDynamicPlushEntity getDynamicPlushEntity(){
        if(dynamicPlushEntity==null){
            dynamicPlushEntity=new CacheDynamicPlushEntity("dynamicPlushCache",GROUP_DETAIL_CACHE_TYPE);
        }
        return dynamicPlushEntity;
    }

    public CacheGroupEntity groupDetail(){
        if(groupDetailCache == null){
            groupDetailCache = new CacheGroupEntity("groupDetailCache", GROUP_DETAIL_CACHE_TYPE);
        }
        return groupDetailCache;
    }
    
    public CacheFriendApplyEntity friendApply(){
        if(friendApplyCache == null){
            UserEntity latest = user().getLatest();
            String id = latest == null ? "0" : latest.getAccount();
            friendApplyCache = new CacheFriendApplyEntity("friendApplyCache" + id,
                    FRIEND_APPLY_CACHE_TYPE);
        }
        return friendApplyCache;
    }


    public CacheGroupApplyEntity groupApply(){
        if(groupApplyCache == null){
            UserEntity latest = user().getLatest();
            String id = latest == null ? "0" : latest.getAccount();
            groupApplyCache = new CacheGroupApplyEntity("groupApplyCache" + id,
                    GROUP_APPLY_CACHE_TYPE);
        }
        return groupApplyCache;
    }
    
    public CacheRealNameAuthEntity realNameAuth(){
        if(realNameAuthCache == null){
            realNameAuthCache = new CacheRealNameAuthEntity("realNameAuth" + user().getLatest().getId(),
                    REAL_NAME_AUTH_CACHE_TYPE);
        }
        return realNameAuthCache;
    }
    public CacheBankCardEntity bankCard(){
        if(bankCardCache == null){
            bankCardCache = new CacheBankCardEntity("bankCard" + user().getLatest().getId(),
                    BANK_CARD_CACHE_TYPE);
        }
        return bankCardCache;
    }
}
