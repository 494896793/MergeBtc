package com.bochat.app.common.model;

import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupMemberEntity;

import java.util.List;

/**
 * 2019/4/28
 * Author LDL
 **/
public interface IGroupLocalModel {

    /*群聊列表本地化*/
    public void saveOrUpdateGroupList(List<GroupEntity> items);

    /*根据群id本地中查找群*/
    public GroupEntity findGroupById(long group_id);

    /*群成员本地化*/
    public void saveOrUpdateGroupMember(List<GroupMemberEntity> groupMemberEntities);
    
    /*获取所有本地群列表*/
    public List<GroupEntity> findAllGroup();

    /*根据群id查找该群所有成员*/
    public List<GroupMemberEntity> findGroupMembersByGroupId(long groupId);

}