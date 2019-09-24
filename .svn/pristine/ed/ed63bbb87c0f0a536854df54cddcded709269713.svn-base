package com.bochat.app.business.fetcher;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.bean.GroupMemberListEntity;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.greendao.GroupMemberEntityDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/29 14:37
 * Description :
 */

public class GroupMemberProvider {
    
    private IGroupModule groupModule;

    private GroupDetailProvider groupDetailProvider;
    
    public GroupMemberProvider(IGroupModule groupModule) {
        this.groupModule = groupModule;
        groupDetailProvider = new GroupDetailProvider(groupModule);
    }

    private List<GroupMemberEntity> query(long groupId){
        
        GroupEntity groupEntity = groupDetailProvider.getGroupDetail(groupId, false);
        if(groupEntity == null){
            groupEntity = groupDetailProvider.getGroupDetail(groupId, true);
        }
        if(groupEntity != null){
            List<GroupMemberEntity> groupMembers = DBManager.getInstance().findGroupMembersByGroupId(groupId);
            DBManager.getInstance().getGroupMemberEntityDao().deleteInTx(groupMembers);
            groupMembers.clear();

            GroupMemberListEntity listEntity = groupModule.queryGroupMembers(groupId, 0, 0);
            if (listEntity == null || listEntity.getRetcode() != 0) {
                return null;
            }

            ArrayList<GroupMemberEntity> memberList = new ArrayList<>();
            if (listEntity.getData() != null) {
                memberList.addAll(listEntity.getData());
            }

            GroupMemberEntity owner = null;
            ArrayList<GroupMemberEntity> managers = new ArrayList<>();
            ArrayList<GroupMemberEntity> members = new ArrayList<>();
            for(GroupMemberEntity member : memberList){
                if(member.getRole() == 3){
                    owner = member;
                } else if(member.getRole() == 2){
                    managers.add(member);
                } else {
                    members.add(member);
                }
            }
            GroupMemberEntity ownerTitle = new GroupMemberEntity();
            ownerTitle.setGroup_id(groupId);
            ownerTitle.setRole(4);
            ownerTitle.setNickname("群主/管理员("+(1+managers.size())+")");
            GroupMemberEntity memberTitle = new GroupMemberEntity();
            memberTitle.setGroup_id(groupId);
            memberTitle.setRole(4);
            memberTitle.setNickname("成员("+(members.size())+")");
            memberList = new ArrayList<>();
            if(owner!=null){
                memberList.add(ownerTitle);
                memberList.add(owner);
            }
            memberList.addAll(managers);
            memberList.add(memberTitle);
            memberList.addAll(members);
            
            DBManager.getInstance().saveGroupMemberListEntity(memberList);
            
            groupEntity.setMember_num(listEntity.getData().size());
            CachePool.getInstance().groupDetail().put(groupEntity);
            
            return memberList;
        }
        return null;
    }
    
    public List<GroupMemberEntity> getGroupMemberList(long groupId, boolean isForceUpdate){
        if (isForceUpdate) {
            return query(groupId);
        }
        List<GroupMemberEntity> cache = DBManager.getInstance().findGroupMembersByGroupId(groupId);
        if(cache == null || cache.isEmpty()){
            return query(groupId);
        }
        return cache;
    }
    
    public GroupMemberEntity getGroupMember(long groupId, long userId){
        List<GroupMemberEntity> list = DBManager.getInstance().getGroupMemberEntityDao().queryBuilder().where(
                GroupMemberEntityDao.Properties.Group_id.eq(groupId)
                , GroupMemberEntityDao.Properties.Member_id.eq(userId)
                , GroupMemberEntityDao.Properties.Role.notEq(4)
        ).list();
        if(!list.isEmpty()){
            return list.get(0);
        }
        List<GroupMemberEntity> query = query(groupId);
        if(query != null){
            list = DBManager.getInstance().getGroupMemberEntityDao().queryBuilder().where(
                    GroupMemberEntityDao.Properties.Group_id.eq(groupId)
                    , GroupMemberEntityDao.Properties.Member_id.eq(userId)
                    , GroupMemberEntityDao.Properties.Role.notEq(4)).list();
            if(!list.isEmpty()){
                return list.get(0);
            }   
        }
        return null;
    }
}
