package com.bochat.app.model.modelImpl;

import android.text.TextUtils;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.model.IGroupLocalModel;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.util.MD5Util;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupApplyServerListEntity;
import com.bochat.app.model.bean.GroupCreateEntity;
import com.bochat.app.model.bean.GroupDataEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupForbiddenListEntity;
import com.bochat.app.model.bean.GroupJionQuestionAnwerEntity;
import com.bochat.app.model.bean.GroupLevelListEntity;
import com.bochat.app.model.bean.GroupListEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.bean.GroupMemberListEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.NewGroupManagerListEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;
import com.bochat.app.model.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 2019/4/16
 * Author LDL
 **/
public class GroupModule implements IGroupModule, IGroupLocalModel {

    @Override
    public CodeEntity dealGroupApply(String gapply_id, String gapply_state, String refuse_text) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity)HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).handleGroupApply(Api.handleGroupApply,gapply_id,gapply_state,refuse_text));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public GroupCreateEntity createGroup(String userId, String group_name, String introduce, String label, String head) {
        GroupCreateEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity)HttpClient.getInstance().sends(GroupCreateEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                .createGroup(Api.createGroup,userId,group_name,introduce,label,head)
        );
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new GroupCreateEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity changeGroupInfo(String head, String level, String introduce, String label, String target, long group_id, String group_name, String tradepwd, double price){
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity=null;
        if(TextUtils.isEmpty(tradepwd)){
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).updateChange(
                    Api.updateChange,head, introduce, label, group_id, group_name
            ));
        } else {
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).updateChange(
                    Api.updateChange,head,level,introduce,label,
                    target,group_id,
                    group_name, MD5Util.lock(tradepwd),price
            ));
        }
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity dissolutionGroup(long group_id, String userId) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).dissmissGroup(
                Api.dissmissGroup,group_id,userId
        ));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity quitGroup(long group_id) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).quitGroup(
                Api.quitGroup,group_id
        ));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity handleGroupApply(String applyId, String applyState, String refuseText) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).handleGroupApply(
                Api.handleGroupApply,applyId,applyState,refuseText
        ));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public GroupListEntity getGroupInfo(long group_id, String keyword, int start, int offset) {
        GroupListEntity groupEntity=null;
        HttpClientEntity httpClientEntity=null;
        if(group_id==-1){
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                    .sends(GroupListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                            .queryGroup(Api.getUpdateChange,keyword,start,offset));
        }else{
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(GroupDataEntity.class,HttpClient.getInstance().retrofit()
                    .create(RetrofitService.class)
                    .queryGroupExact(Api.getUpdateChange,group_id,0,0));
        }
        if(httpClientEntity.getCode()==0){
            if(group_id==-1){
                groupEntity=httpClientEntity.getObj();
            }else{
                groupEntity=new GroupListEntity();
                groupEntity.setCurrentPage(1);
                groupEntity.setPageSize(1);
                groupEntity.setIsNext(0);
                groupEntity.setTotalPage(1);
                groupEntity.setTotalCount(1);
                List<GroupEntity> list=new ArrayList<>();
                if(httpClientEntity.getObj()!=null){
                    GroupEntity groupEntity1=((GroupDataEntity)httpClientEntity.getObj()).getData();
                    list.add(groupEntity1);
                }
                groupEntity.setItems(list);
            }
        }else{
            groupEntity=new GroupListEntity();
            groupEntity.setCode(httpClientEntity.getCode());
            groupEntity.setMsg(httpClientEntity.getMessage());
            groupEntity.setRetcode(httpClientEntity.getCode());
        }
        return groupEntity;
    }
    
    @Override
    public GroupEntity getMyGroupApply(String userId, int start, int offset) {
        return null;
    }

    @Override
    public CodeEntity addGroupManager(int group_id,String groupToken,int targetId,String targetToken) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).addGroupManager(
                Api.addGroupManager,group_id,groupToken,targetId,targetToken
        ));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public GroupListEntity queryMyGroupList(String userId, int start, int offset) {
        GroupListEntity groupListEntity=null;
        HttpClientEntity httpClientEntity=null;
        if(start==0&&offset==0){
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(GroupListEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class)
                    .queryMyGroupList(Api.queryMyGroupList,userId,start,offset)
            );
        }else{
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(GroupListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                    .queryMyGroupList(Api.queryMyGroupList,userId,start,offset)
            );
        }
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            groupListEntity = httpClientEntity.getObj();
            if(start==0&&offset==0){
                if(groupListEntity!=null){
                    groupListEntity.setItems(groupListEntity.getData());
                }
            }
        }else{
            groupListEntity=new GroupListEntity();
            groupListEntity.setCode(httpClientEntity.getCode());
            groupListEntity.setMsg(httpClientEntity.getMessage());
            groupListEntity.setRetcode(httpClientEntity.getCode());
        }
        return groupListEntity;
    }

    @Override
    public GroupMemberListEntity queryGroupMembers(long group_id, int start, int offset) {
        GroupMemberListEntity groupMemberListEntity=null;
        HttpClientEntity httpClientEntity=null;
        if(start==0&&offset==0){
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(GroupMemberListEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).queryGroupMembers(Api.queryGroupMembers,group_id,start,offset));
        }else{
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(GroupMemberListEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).queryGroupMembers(Api.queryGroupMembers,group_id,start,offset));
        }
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            groupMemberListEntity=httpClientEntity.getObj();
            if(start==0&&offset==0){
                if(groupMemberListEntity!=null){
                    groupMemberListEntity.setItems(groupMemberListEntity.getData());
                }
            }
        }else{
            groupMemberListEntity=new GroupMemberListEntity();
            groupMemberListEntity.setCode(httpClientEntity.getCode());
            groupMemberListEntity.setMsg(httpClientEntity.getMessage());
            groupMemberListEntity.setRetcode(httpClientEntity.getCode());
        }
        return groupMemberListEntity;
    }
    @Override
    public GroupApplyServerListEntity queryGroupApplys(int start, int offset) {
        GroupApplyServerListEntity groupApplyListEntity=null;
        HttpClientEntity httpClientEntity=null;
        if(start==0&&offset==0){
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(GroupApplyServerListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).queryGroupApply(Api.queryGroupApply,start,offset));
        }else{
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(GroupApplyServerListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).queryGroupApply(Api.queryGroupApply,start,offset));
        }
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            groupApplyListEntity=httpClientEntity.getObj();
        }else{
            groupApplyListEntity=new GroupApplyServerListEntity();
            groupApplyListEntity.setCode(httpClientEntity.getCode());
            groupApplyListEntity.setMsg(httpClientEntity.getMessage());
            groupApplyListEntity.setRetcode(httpClientEntity.getCode());
        }
        return groupApplyListEntity;
    }

    @Override
    public CodeEntity joinGroup(String group_id, String group_name, String message) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).joinGroup(Api.joinGroup,group_id,group_name, message));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }
    
    @Override
    public GroupLevelListEntity queryGroupLevel(String group_id) {
        GroupLevelListEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(GroupLevelListEntity.class, HttpClient.getInstance().retrofit().create(RetrofitService.class).queryGroupLevel(Api.queryGroupLevel,group_id));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new GroupLevelListEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity setGroupAuth(String group_id, int joinType, String question, String answer) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                .sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                .setGroupAuth(Api.setGroupAuth,group_id,joinType, question,answer));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity muteAllMembers(String groupid, String prohibitState) {

        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                .sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                        .muteAllMembers(Api.muteAllMembers,groupid,prohibitState));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public GroupForbiddenListEntity queryMuteMembers(String start, String offset, String groupId) {

        GroupForbiddenListEntity groupForbiddenList=null;
        HttpClientEntity httpClientEntity = null;

        httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(GroupForbiddenListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                    .queryMuteMembers(Api.queryMuteMembers,start,offset,groupId));


        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){

            groupForbiddenList = httpClientEntity.getObj();

        }else{
            groupForbiddenList=new GroupForbiddenListEntity();
            groupForbiddenList.setCode(httpClientEntity.getCode());
            groupForbiddenList.setMsg(httpClientEntity.getMessage());
            groupForbiddenList.setRetcode(httpClientEntity.getCode());
        }


        return groupForbiddenList;
    }

    @Override
    public CodeEntity muteMembers(String groupId, String memberId, String prohibitState) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                .sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                        .muteMembers(Api.muteMembers,groupId,memberId,prohibitState));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity adminQuitGroup(long groupId, String[] groupUserIdArray) {
        String groupUserId= groupUserIdArray[0];
        List<String> list = new ArrayList<>();
        list.add(groupUserId);
        LogUtil.LogDebug("ggyy","groupUserId ="+groupUserId);
        CodeEntity codeEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance()
                .sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                .adminQuitGroup(Api.adminQuitGroup,groupId,groupUserId));
                if (httpClientEntity.getCode() == Constan.NET_SUCCESS){
                    codeEntity = httpClientEntity.getObj();
                }else {
                    codeEntity=new CodeEntity();
                    codeEntity.setCode(httpClientEntity.getCode());
                    codeEntity.setMsg(httpClientEntity.getMessage());
                    codeEntity.setRetcode(httpClientEntity.getCode());
                }
        return codeEntity;
    }

    @Override
    public CodeEntity addGroupManager(String groupId, String targetId) {
        CodeEntity codeEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance()
                .sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                        .addGroupManager(Api.addGroupManager,groupId,targetId));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS){
            codeEntity = httpClientEntity.getObj();
        }else {
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public NewGroupManagerListEntity queryGroupManager(String start, String offset, String groupId) {
        NewGroupManagerListEntity groupManagerList=null;
        HttpClientEntity httpClientEntity = null;

        httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(NewGroupManagerListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                .queryGroupManager(Api.queryGroupManager,start,offset,groupId));

        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){

            groupManagerList = httpClientEntity.getObj();

        }else{
            groupManagerList=new NewGroupManagerListEntity();
            groupManagerList.setCode(httpClientEntity.getCode());
            groupManagerList.setMsg(httpClientEntity.getMessage());
            groupManagerList.setRetcode(httpClientEntity.getCode());
        }

//        LogUtil.LogDebug("ggyy","ggyy"+groupManagerList+ " ----"+groupManagerList.getItem().toString());
        return groupManagerList;
    }

    @Override
    public CodeEntity deleteGroupManager(String groupId, String userId) {
        CodeEntity codeEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance()
                .sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                        .deleteGroupManager(Api.deleteGroupManager,groupId,userId));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS){
            codeEntity = httpClientEntity.getObj();
        }else {
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public GroupJionQuestionAnwerEntity getGroupVerify(String groupId) {
        GroupJionQuestionAnwerEntity codeEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance()
                .sends(GroupJionQuestionAnwerEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                        .getGroupVerify(Api.getGroupVerify,groupId));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS){
            codeEntity = httpClientEntity.getObj();
        }else {
            codeEntity=new GroupJionQuestionAnwerEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity invitejoinGroup(String group_id, String targetId, String name) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).invitejoinGroup(Api.invitejoinGroup,group_id,targetId, name));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }

        return codeEntity;
    }

    @Override
    public void saveOrUpdateGroupList(List<GroupEntity> items) {
        CachePool.getInstance().group().clear();
        CachePool.getInstance().group().put(items);
    }

    @Override
    public GroupEntity findGroupById(long group_id) {
        return CachePool.getInstance().group().get(group_id);
    }

    @Override
    public void saveOrUpdateGroupMember(List<GroupMemberEntity> groupMemberEntities) {
        if(groupMemberEntities != null && !groupMemberEntities.isEmpty()){
            DBManager.getInstance().saveGroupMemberListEntity(groupMemberEntities);
        }
    }
    
    @Override
    public List<GroupEntity> findAllGroup() {
        return CachePool.getInstance().group().getAll();
    }

    @Override
    public List<GroupMemberEntity> findGroupMembersByGroupId(long groupId) {
        return DBManager.getInstance().findGroupMembersByGroupId(groupId);
    }
}
