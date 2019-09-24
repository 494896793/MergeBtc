package com.bochat.app.model.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.util.LogUtil;

/**
 * 2019/4/12
 * Author LDL
 **/
public class BoChatDbHelper {

    private DaoSession daoSession;

    private Context mContext;

    private static BoChatDbHelper instance;

    public BoChatDbHelper(){

    }

    public static void init(Context context){
        instance=new BoChatDbHelper(context);
    }

    public BoChatDbHelper(Context context){
        this.mContext=context;
        instance=this;
    }

    public static BoChatDbHelper getInstance(){
//        if(instance==null){
//            synchronized (BochatDbHelper.class){
//                instance=new BochatDbHelper();
//            }
//        }
        return instance;
    }

    public synchronized void initDatabase() {
        try{
            BochatDbOpenHelper openHelper = new BochatDbOpenHelper(mContext, Constan.DbName);
            SQLiteDatabase db = openHelper.getWritableDatabase();
            DaoMaster master = new DaoMaster(db);
            daoSession = master.newSession();
            LogUtil.LogDebug("database", "database[" + Constan.DbName + "] initial successfully, daoSession is " + daoSession);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public UserEntityDao getUserDao(){
        UserEntityDao userEntityDao=null;
        if(daoSession!=null){
            userEntityDao=daoSession.getUserEntityDao();
        }
        return userEntityDao;
    }

    public FriendApplyEntityDao getFriendApplyDao(){
        FriendApplyEntityDao friendApplyEntityDao=null;
        if(daoSession!=null){
            friendApplyEntityDao=daoSession.getFriendApplyEntityDao();
        }
        return friendApplyEntityDao;
    }

    public GroupMemberEntityDao getGroupMemberEntityDao(){
        GroupMemberEntityDao groupMemberEntityDao=null;
        if(daoSession!=null){
            groupMemberEntityDao=daoSession.getGroupMemberEntityDao();
        }
        return groupMemberEntityDao;
    }

    public FriendEntityDao getFriendEntityDao(){
        FriendEntityDao friendEntityDao=null;
        if(daoSession!=null){
            friendEntityDao=daoSession.getFriendEntityDao();
        }
        return friendEntityDao;
    }
    public TeamEntityDao getTeamEntityDao(){
        TeamEntityDao teamEntityDao=null;
        if(daoSession!=null){
            teamEntityDao=daoSession.getTeamEntityDao();
        }
        return teamEntityDao;
    }

    public RedPacketStatuEntityDao getRedPacketStatuEntityDao(){
        RedPacketStatuEntityDao redPacketStatuEntityDao=null;
        if(daoSession!=null){
            redPacketStatuEntityDao=daoSession.getRedPacketStatuEntityDao();
        }
        return redPacketStatuEntityDao;
    }

    public SpeedConverStatusEntityDao getSpeedConverStatusEntityDao(){
        SpeedConverStatusEntityDao speedConverStatusEntityDao=null;
        if(daoSession!=null){
            speedConverStatusEntityDao=daoSession.getSpeedConverStatusEntityDao();
        }
        return speedConverStatusEntityDao;
    }
    public GroupApplyEntityDao getGroupApplyEntityDao(){
        GroupApplyEntityDao groupApplyEntityDao=null;
        if(daoSession!=null){
            groupApplyEntityDao=daoSession.getGroupApplyEntityDao();
        }
        return groupApplyEntityDao;
    }

    public RedPacketPeopleEntityDao getRedPacketPeopleEntityDao(){
        RedPacketPeopleEntityDao packetPeopleEntityDao=null;
        if(daoSession!=null){
            packetPeopleEntityDao=daoSession.getRedPacketPeopleEntityDao();
        }
        return  packetPeopleEntityDao;
    }

    public GroupEntityDao getGroupEntityDao(){
        GroupEntityDao groupEntityDao=null;
        if(daoSession!=null){
            groupEntityDao=daoSession.getGroupEntityDao();
        }
        return groupEntityDao;
    }
    
    public UserCurrencyEntityDao getUserCurrencyEntityDao(){
        UserCurrencyEntityDao userCurrencyEntityDao=null;
        if(daoSession!=null){
            userCurrencyEntityDao=daoSession.getUserCurrencyEntityDao();
        }
        return userCurrencyEntityDao;
    }

    public synchronized void release() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }

        instance = null;
    }

}
