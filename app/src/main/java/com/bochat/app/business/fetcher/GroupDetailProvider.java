package com.bochat.app.business.fetcher;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupListEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/29 16:17
 * Description :
 */

public class GroupDetailProvider {
    
    private IGroupModule groupModule;
    
    public GroupDetailProvider(IGroupModule groupModule) {
        this.groupModule = groupModule;
    }
    
    public GroupEntity getGroupDetail(long groupId, boolean isForceUpdate){
        GroupEntity group = CachePool.getInstance().groupDetail().get(groupId);
        if(!isForceUpdate && group != null){
            return group;
        }
        GroupListEntity groupInfo = groupModule.getGroupInfo(groupId, "", -1, -1);
        if (groupInfo == null || groupInfo.getRetcode() != 0) {
            return null;
        }
        int memberNum = group == null ? 0 : group.getMember_num();
        group = groupInfo.getItems().get(0);
        group.setMember_num(memberNum);
        CachePool.getInstance().groupDetail().put(group);
        return group;
    }
}