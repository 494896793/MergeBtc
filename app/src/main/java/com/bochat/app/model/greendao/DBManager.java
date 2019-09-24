package com.bochat.app.model.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bochat.app.common.util.SharePreferenceUtil;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.bean.RedPacketPeopleEntity;
import com.bochat.app.model.bean.RedPacketStatuEntity;
import com.bochat.app.model.bean.SpeedConverStatusEntity;
import com.bochat.app.model.bean.TeamEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.listenner.DatabaseInterface;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 2019/4/12
 * Author LDL
 **/
public class DBManager implements DatabaseInterface {

    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private UserEntityDao userEntityDao;
    private DaoSession daoSession;
    private FriendApplyEntityDao friendApplyEntityDao;
    private GroupMemberEntityDao groupMemberEntityDao;
    private GroupEntityDao groupEntityDao;
    private FriendEntityDao friendEntityDao;
    private TeamEntityDao teamEntityDao;
    private RedPacketStatuEntityDao redPacketStatuEntityDao;
    private SpeedConverStatusEntityDao speedConverStatusEntityDao;
    private GroupApplyEntityDao groupApplyEntityDao;
    private RedPacketPeopleEntityDao packetPeopleEntityDao;
    private UserCurrencyEntityDao userCurrencyEntityDao;
    
    public DBManager(Context context) {
        mInstance=this;
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, Constan.DbName, null);
        userEntityDao=BoChatDbHelper.getInstance().getUserDao();
        friendApplyEntityDao=BoChatDbHelper.getInstance().getFriendApplyDao();
        groupMemberEntityDao=BoChatDbHelper.getInstance().getGroupMemberEntityDao();
        groupEntityDao=BoChatDbHelper.getInstance().getGroupEntityDao();
        friendEntityDao=BoChatDbHelper.getInstance().getFriendEntityDao();
        teamEntityDao = BoChatDbHelper.getInstance().getTeamEntityDao();
        redPacketStatuEntityDao=BoChatDbHelper.getInstance().getRedPacketStatuEntityDao();
        speedConverStatusEntityDao=BoChatDbHelper.getInstance().getSpeedConverStatusEntityDao();
        groupApplyEntityDao = BoChatDbHelper.getInstance().getGroupApplyEntityDao();
        packetPeopleEntityDao=BoChatDbHelper.getInstance().getRedPacketPeopleEntityDao();
        userCurrencyEntityDao = BoChatDbHelper.getInstance().getUserCurrencyEntityDao();
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager init(Context context) {
        if(context!=null){
            if (mInstance == null) {
                synchronized (DBManager.class) {
                    if (mInstance == null) {
                        mInstance = new DBManager(context);
                    }
                }
            }
        }
        return mInstance;
    }

    public void clearAllRedPacket(){
        RedPacketStatuEntity entity=null;
        if(redPacketStatuEntityDao!=null){
            redPacketStatuEntityDao.deleteAll();
            Log.i("===","===");
        }
    }

    public void clearFriendById(long id){
        try{
            if(friendEntityDao!=null){
                friendEntityDao.deleteByKey(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearAllFriend(){
        try{
            if(friendEntityDao!=null){
                friendEntityDao.deleteAll();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearAllFriendApply(){
        try{
            if(friendApplyEntityDao!=null){
                friendApplyEntityDao.deleteAll();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearFriendApplyById(String id){
        try{
            if(friendApplyEntityDao!=null){
                friendApplyEntityDao.deleteByKey(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static DBManager getInstance(){
        return mInstance;
    }

    public void clearAllGroup(){
        try{
            if(groupEntityDao!=null){
                groupEntityDao.deleteAll();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearGroupById(long group_id){
        try{
            if(groupEntityDao!=null){
                groupEntityDao.deleteByKey(group_id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveOrUpdateRedPacket(RedPacketStatuEntity statuEntity){
        try{
            if(redPacketStatuEntityDao!=null){
                redPacketStatuEntityDao.insertOrReplaceInTx(statuEntity);
                Log.i(Constan.TAG,"红包本地化成功");
            }else{
                Log.i(Constan.TAG,"红包本地化失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveOrUpdateRedPacketPeopleEntity(RedPacketPeopleEntity entity){
        if(packetPeopleEntityDao!=null){
            packetPeopleEntityDao.insertOrReplace(entity);
            Log.i(Constan.TAG,"个人领取状态本地化成功");
        }else{
            Log.i(Constan.TAG,"个人领取状态本地化失败");
        }
    }

    public List<RedPacketPeopleEntity> findAllRedPacketPeople(){
        List<RedPacketPeopleEntity> list=null;
        if(packetPeopleEntityDao!=null){
            list=packetPeopleEntityDao.queryBuilder().list();
        }
        return list;
    }

    public List<RedPacketPeopleEntity> findAllRedPacketPeopleById(long id){
        List<RedPacketPeopleEntity> list=null;
        if(packetPeopleEntityDao!=null){
            list=packetPeopleEntityDao.queryBuilder().where(RedPacketPeopleEntityDao.Properties.Reward_id.eq(id)).list();
        }
        return list;
    }

    public RedPacketPeopleEntity findRedPacketPeopleById(long id){
        RedPacketPeopleEntity entity=null;
        if(packetPeopleEntityDao!=null){
            List<RedPacketPeopleEntity> list=packetPeopleEntityDao.queryBuilder().where(RedPacketPeopleEntityDao.Properties.Reward_id.eq(id)).list();
            if(list!=null&&list.size()!=0){
                entity=list.get(0);
            }
        }
        return entity;
    }

    public SpeedConverStatusEntity getSpeedConverById(int id){
        SpeedConverStatusEntity entity=null;
        if(redPacketStatuEntityDao!=null){
            List<SpeedConverStatusEntity> list=speedConverStatusEntityDao.queryBuilder().where(SpeedConverStatusEntityDao.Properties.Id.eq(id)).list();
            if(list!=null&&list.size()!=0){
                entity=list.get(0);
            }
        }
        return entity;
    }

    public void saveOrUpdateSpeedConverStatu(SpeedConverStatusEntity entity){
        if(speedConverStatusEntityDao!=null){
            speedConverStatusEntityDao.insertOrReplace(entity);
            Log.i(Constan.TAG,"闪兑状态本地化成功");
        }else{
            Log.i(Constan.TAG,"闪兑状态本地化失败");
        }
    }

    public List<RedPacketStatuEntity> findAllRed(){
        List<RedPacketStatuEntity> list=null;
        if(redPacketStatuEntityDao!=null){
            list=redPacketStatuEntityDao.queryBuilder().list();
        }
        return list;
    }

    public void findAllRedStatu(){
        RedPacketStatuEntity entity=null;
        if(redPacketStatuEntityDao!=null){
            List<RedPacketStatuEntity> list=redPacketStatuEntityDao.queryBuilder().list();
           Log.i("===","===");
        }
    }

    public RedPacketStatuEntity getRedPacketById(int id){
        RedPacketStatuEntity entity=null;
        if(redPacketStatuEntityDao!=null){
            List<RedPacketStatuEntity> list=redPacketStatuEntityDao.queryBuilder().where(RedPacketStatuEntityDao.Properties.Id.eq(id)).list();
            if(list!=null&&list.size()!=0){
                entity=list.get(0);
            }
        }
        return entity;
    }

    @Deprecated
    public UserEntity getUserInfo(){
        UserEntity userEntity=null;
        String userName=SharePreferenceUtil.getAccount();
        UserEntityDao userEntityDao=BoChatDbHelper.getInstance().getUserDao();
        if(userName!=null&&userEntityDao!=null){
            QueryBuilder<UserEntity> qb=userEntityDao.queryBuilder();
            List<UserEntity> userEntities=qb.where(UserEntityDao.Properties.Account.eq(userName)).list();
            if(userEntities.size()!=0){
                return userEntities.get(0);
            }
        }else{
            Log.i(Constan.TAG,"数据库获取用户信息失败: userName=null || userEntityDao=null");
        }
        return userEntity;
    }


    @Deprecated
    public void saveOrUpdateFriendList(List<FriendEntity> list){
        if(friendEntityDao!=null){
            friendEntityDao.insertOrReplaceInTx(list);
            Log.i(Constan.TAG,"好友列表数据库同步成功");
        }else{
            Log.i(Constan.TAG,"好友列表数据库同步失败");
        }
    }

    @Deprecated
    public FriendEntity findFriendById(long friendId){
        FriendEntity friendEntity=null;
        if(friendEntityDao!=null){
            if(friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Id.eq(friendId)).list()!=null&&friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Id.eq(friendId)).list().size()!=0){
                friendEntity=friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Id.eq(friendId)).list().get(0);
            }
        }
        return friendEntity;
    }
    
    public void saveOrUpdateFriendEntity(FriendEntity friendEntity){
        try{
            if(friendEntityDao!=null){
                friendEntityDao.insertOrReplace(friendEntity);
                Log.i(Constan.TAG,"好友列表数据库同步成功");
            }else{
                Log.i(Constan.TAG,"好友列表数据库同步失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Deprecated
    public List<FriendEntity> findAllFriendList(){
        List<FriendEntity> list=null;
        if(friendEntityDao!=null){
            list=friendEntityDao.queryBuilder().list();
        }
        return list;
    }

    @Deprecated
    public List<TeamEntity> findallTeamList(){
        List<TeamEntity> list=null;
        if(teamEntityDao!=null){
            list=teamEntityDao.queryBuilder().list();
        }
        return list;
    }


    public void saveOrUpdateTeamEntity(TeamEntity teamEntity){
        try{
            if(teamEntityDao!=null){
                teamEntityDao.insertOrReplace(teamEntity);
                Log.i(Constan.TAG,"团队列表数据库同步成功");
            }else{
                Log.i(Constan.TAG,"团队列表数据库同步失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Deprecated
    public void saveOrUpdateTeamList(List<TeamEntity> list){
        if(teamEntityDao!=null){
            teamEntityDao.insertOrReplaceInTx(list);
            Log.i(Constan.TAG,"团队列表数据库同步成功");
        }else{
            Log.i(Constan.TAG,"团队列表数据库同步失败");
        }
    }

    public void clearAllTeam(){
        try{
            if(teamEntityDao!=null){
                teamEntityDao.deleteAll();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void clearTeamById(long id){
        try{
            if(teamEntityDao!=null){
                teamEntityDao.deleteByKey(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    public void deleteFriendApply(){
        if(friendApplyEntityDao!=null){
            friendApplyEntityDao.deleteAll();
        }
    }
    
    public List<GroupMemberEntity> findGroupMembersByGroupId(long groupId){
        List<GroupMemberEntity> list=null;
        if(groupMemberEntityDao!=null){
            list=groupMemberEntityDao.queryBuilder().where(GroupMemberEntityDao.Properties.Group_id.eq(groupId)).list();
        }
        return list;
    }
    
    public List<GroupMemberEntity> findGroupMembers(int groupId, int start, int count){
        List<GroupMemberEntity> list=null;
        if(groupMemberEntityDao!=null){
            list=groupMemberEntityDao.queryBuilder().where(
                    GroupMemberEntityDao.Properties.Group_id.eq(groupId),
                    GroupMemberEntityDao.Properties.None_id.ge(start),
                    GroupMemberEntityDao.Properties.Group_id.lt(start+count)).list();
        }
        return list;
    }

    @Deprecated
    public List<GroupEntity> findAllGroup(){
        List<GroupEntity> list=null;
        if(groupEntityDao!=null){
            list=groupEntityDao.queryBuilder().list();
        }
        return list;
    }
    
    public void saveGroupMemberListEntity(List<GroupMemberEntity> items){
        if(groupMemberEntityDao!=null){
            groupMemberEntityDao.insertOrReplaceInTx(items);
            Log.i(Constan.TAG,"群成员信息数据库同步成功");
        }else{
            Log.i(Constan.TAG,"群成员信息数据库同步失败");
        }
    }
    
    public GroupMemberEntityDao getGroupMemberEntityDao(){
        return groupMemberEntityDao;
    }
    
    public GroupApplyEntityDao getGroupApplyEntityDao(){
        return groupApplyEntityDao;
    }
    
    public UserCurrencyEntityDao getUserCurrencyEntityDao(){
        return userCurrencyEntityDao;
    }
    
    @Deprecated
    public GroupMemberEntity findGroupMemberById(int member_id){
        GroupMemberEntity groupMemberEntity=null;
        if(groupMemberEntityDao!=null){
            List<GroupMemberEntity> list=groupMemberEntityDao.queryBuilder().where(GroupMemberEntityDao.Properties.Member_id.eq(member_id)).list();
            if(list!=null&&list.size()>0){
                groupMemberEntity=list.get(0);
            }
        }
        return groupMemberEntity;
    }

    @Deprecated
    public void saveOrUpdateGroup(GroupEntity groupEntity){
        if(groupEntityDao!=null){
            groupEntityDao.insertOrReplace(groupEntity);
            Log.i(Constan.TAG,"群信息数据库同步成功");
        }else{
            Log.i(Constan.TAG,"群信息数据库同步失败");
        }
    }

    @Deprecated
    public void saveOrUpdateGroupList(List<GroupEntity> items){
        if(groupEntityDao!=null&&items!=null){
            groupEntityDao.insertOrReplaceInTx(items);
            Log.i(Constan.TAG,"群信息数据库批量操作成功");
        }else{
            Log.i(Constan.TAG,"群信息数据库批量操作失败");
        }
    }

    @Deprecated
    public GroupEntity findGroupById(long groupId){
        GroupEntity groupEntity=null;
        if(groupEntityDao!=null){
            List<GroupEntity> list=groupEntityDao.queryBuilder().where(GroupEntityDao.Properties.Group_id.eq(groupId)).list();
            if(list!=null&&list.size()!=0){
                groupEntity=list.get(0);
            }
        }
        return groupEntity;
    }


    /*保存或更新用户信息*/
    @Deprecated
    public void saveOrUpdateUser(UserEntity userEntity){
        if(userEntityDao!=null){
            userEntityDao.insertOrReplace(userEntity);
            SharePreferenceUtil.saveAccount(userEntity.getAccount());
            Log.i(Constan.TAG,"用户信息数据库同步成功");
        }else{
            Log.i(Constan.TAG,"用户信息数据库同步失败:userEntityDao=null");
        }
    }

    @Deprecated
    public List<FriendApplyEntity> findFriendApplyById(String proposer_id){
        List<FriendApplyEntity> list=null;
        if(friendApplyEntityDao!=null){
            list=friendApplyEntityDao.queryBuilder().where(FriendApplyEntityDao.Properties.Proposer_id.eq(proposer_id)).list();
        }
        return list;
    }

    @Deprecated
    public void saveOrUpdateFriendApply(FriendApplyEntity friendApplyEntity){
        if(friendApplyEntityDao!=null){
            friendApplyEntityDao.insertOrReplace(friendApplyEntity);
            Log.i(Constan.TAG,"好友申请数据库保存成功");
        }else{
            Log.i(Constan.TAG,"好友申请数据库保存失败");
        }
    }

    public void saveOrUpdateFriendApplyList(List<FriendApplyEntity> friendApplyEntities){
        if(friendApplyEntityDao!=null){
            friendApplyEntityDao.insertInTx(friendApplyEntities);
        }
    }

    @Deprecated
    public List<FriendApplyEntity> queryAllFriendApply(){
        List<FriendApplyEntity> list=null;
        try{
            if(friendApplyEntityDao!=null){
                list=friendApplyEntityDao.queryBuilder().list();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Deprecated
    public void updateAllNotReadStatuFriendApply(){
        try{
            if(friendApplyEntityDao!=null){
                List<FriendApplyEntity> list=friendApplyEntityDao.queryBuilder().where(FriendApplyEntityDao.Properties.IsRead.eq("1")).list();
                for(int i=0;i<list.size();i++){
                    list.get(i).setIsRead("0");
                }
                friendApplyEntityDao.updateInTx(list);
                Log.i(Constan.TAG,"好友申请全部已读数据库同步成功");
            }else{
                Log.i(Constan.TAG,"好友申请全部已读数据库同步失败 : friendApplyEntityDao=null");
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i(Constan.TAG,"好友申请全部已读数据库同步失败");
        }
    }

    @Deprecated
    public List<FriendApplyEntity> queryNotReadFriendApply(){
        List<FriendApplyEntity> list=new ArrayList<>();
        try{
            if(friendApplyEntityDao!=null){
                list=friendApplyEntityDao.queryBuilder().where(FriendApplyEntityDao.Properties.IsRead.eq("1")).list();
//                list.addAll(friendApplyEntityDao.queryBuilder().where(FriendApplyEntityDao.Properties.IsRead.eq("0")).list());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Deprecated
    public void saveFriendApplyList(List<FriendApplyEntity> list){
        if(friendApplyEntityDao!=null){
            for(int i=0;i<list.size();i++){
                friendApplyEntityDao.insertOrReplace(list.get(i));
            }
            Log.i(Constan.TAG,"好友申请列表数据库同步成功");
        }else{
            Log.i(Constan.TAG,"好友申请列表数据库同步失败:friendApplyEntityDao=null");
        }
    }

    /**
     * 初始化数据库
     */
    public void initDatabase() {
        release();// 初始化数据库前，先释放掉之前的数据库连接及一些单例，否则会使用同一个数据库--Created by FreddyChen
        BoChatDbHelper.getInstance().initDatabase();
    }

    /**
     * 释放数据库资源
     */
    public void release() {
        BoChatDbHelper.getInstance().release();
        mInstance = null;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, Constan.DbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, Constan.DbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

}
