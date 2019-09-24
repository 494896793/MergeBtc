package com.bochat.app.common.model;

import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupApplyServerListEntity;
import com.bochat.app.model.bean.GroupCreateEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupForbiddenListEntity;
import com.bochat.app.model.bean.GroupJionQuestionAnwerEntity;
import com.bochat.app.model.bean.GroupLevelListEntity;
import com.bochat.app.model.bean.GroupListEntity;
import com.bochat.app.model.bean.GroupMemberListEntity;
import com.bochat.app.model.bean.NewGroupManagerListEntity;

/**
 * 2019/4/15
 * Author LDL
 **/
public interface IGroupModule {


    /*处理进群申请
    gapply_state:1：同意    2：拒绝*/
    public CodeEntity dealGroupApply(String gapply_id,String gapply_state,String refuse_text);

    /*创建群聊
    userId:多个用户以","隔开
    group_name:传用户名，如“张三，李四...”
    token:多个用户以","隔开，第一个用户为群主*/
    public GroupCreateEntity  createGroup(String userId, String group_name, String introduce, String label, String head);

    /*修改群信息*/
    public CodeEntity changeGroupInfo(String head, String level, String introduce, String label, String target, long group_id, String group_name, String tradepwd, double price);

    /*解散群*/
    public CodeEntity dissolutionGroup(long group_id,String userId);
    
    /*退出群*/
    public CodeEntity quitGroup(long group_id);
    
    /*退出群*/
    public CodeEntity handleGroupApply(String applyId, String applyState, String refuseText);

    /*查询群组信息*/
    public GroupListEntity getGroupInfo(long group_id,String keyword,int start,int offset);

    /*群主查询进群申请*/
    public GroupApplyServerListEntity queryGroupApplys(int start, int offset);

    /*查询我的进群申请*/
    public GroupEntity getMyGroupApply(String userId,int start,int offset);

    /*添加群管理员*/
    public CodeEntity addGroupManager(int group_id,String groupToken,int targetId,String targetToken);

    /*查询我的群聊列表*/
    public GroupListEntity queryMyGroupList(String userId,int start,int offset);

    /*查询群成员*/
    public GroupMemberListEntity queryGroupMembers(long group_id, int start, int offset);

    /*加入群聊*/
    public CodeEntity joinGroup(String group_id, String name, String message);
    /*邀请加入群聊*/
    public CodeEntity invitejoinGroup(String group_id,String targetId, String userName);
    
    public GroupLevelListEntity queryGroupLevel(String group_id);
    
    public CodeEntity setGroupAuth(String group_id, int joinType, String question, String answer);

    /*设置群禁言*/
    public CodeEntity muteAllMembers(String groupid,String prohibitState);
    /*展示被禁言成员*/
    public GroupForbiddenListEntity queryMuteMembers(String start, String offset, String groupId);

    /*解除单个成员禁言状态 prohibitState 1:正常， 2.禁言*/
    public CodeEntity muteMembers(String groupId,String memberId, String prohibitState);

    /*移除群成员*/
    public CodeEntity adminQuitGroup(long groupId,String[] groupUserIdArray);

    /*添加管理员*/
    public CodeEntity addGroupManager(String groupId, String targetId);

    /*加载管理员列表*/
    public NewGroupManagerListEntity queryGroupManager(String start, String offset, String groupId);
    /*删除管理员*/
    public CodeEntity  deleteGroupManager(String groupId, String userId);
    /*获取问题和答案*/
    public GroupJionQuestionAnwerEntity getGroupVerify(String groupId);
}