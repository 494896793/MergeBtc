package com.bochat.app.model.modelImpl;

import android.util.Log;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.model.bean.Address;
import com.bochat.app.model.bean.AddressTeamListEntity;
import com.bochat.app.model.bean.BankCardListEntity;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.FriendApplyListEntity;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.InviteCodeEntity;
import com.bochat.app.model.bean.OnLineEntity;
import com.bochat.app.model.bean.ShakyCandyEntity;
import com.bochat.app.model.bean.ShakyStatuEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;

import java.util.ArrayList;
import java.util.List;


/**
 * 2019/4/12
 * Author LDL
 **/
public class UserModule implements IUserModel, IUserLocalModel {


    @Override
    public UserEntity getLocalUserInfo() {
        return CachePool.getInstance().user().getLatest();
    }

    @Override
    public UserEntity getUserInfo() {
        
        UserEntity user = CachePool.getInstance().user().getLatest();
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                .sends(UserEntity.class,"data",HttpClient.getInstance().retrofit()
                        .create(RetrofitService.class).getInfo(Api.GET_USER_INFO,user.getId()+"",user.getToken()));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            UserEntity userEntity=(UserEntity)httpClientEntity.getObj();
            return userEntity;
        }else{
            UserEntity userEntity=new UserEntity();
            userEntity.setCode(httpClientEntity.getCode());
            userEntity.setRetcode(httpClientEntity.getCode());
            userEntity.setMsg(httpClientEntity.getMessage());
            return  userEntity;
        }
    }

    @Override
    public FriendListEntity getFriendListInfo(String userId, String start, String offset) {
        FriendListEntity friendListEntity=null;
        HttpClientEntity httpClientEntity=null;
        
        
        
        if(start.equals("0")&&offset.equals("0")){
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                    .sends(FriendListEntity.class,HttpClient.getInstance().retrofit()
                            .create(RetrofitService.class).getFriendList(Api.GET_FRIEND_LIST_FINO,
                                    CachePool.getInstance().user().getLatest().getToken(),userId,start,offset));
        }else{
            httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                    .sends(FriendListEntity.class,"data",HttpClient.getInstance().retrofit()
                            .create(RetrofitService.class).getFriendList(Api.GET_FRIEND_LIST_FINO,
                                    CachePool.getInstance().user().getLatest().getToken(),userId,start,offset));
        }
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            friendListEntity=httpClientEntity.getObj();
            if(start.equals("0")&&offset.equals("0")){
                if(friendListEntity!=null){
                    friendListEntity.setItems(friendListEntity.getData());
                }
            }
        }else{
            friendListEntity=new FriendListEntity();
            friendListEntity.setCode(httpClientEntity.getCode());
            friendListEntity.setMsg(httpClientEntity.getMessage());
            friendListEntity.setRetcode(httpClientEntity.getCode());
        }
        return friendListEntity;
    }

    @Override
    public FriendListEntity getFriendInfo(String keyword,int start,int offset) {
        FriendListEntity userEntity=null;
        HttpClientEntity httpClientEntity=null;
        if(start==-1||offset==-1){
            httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(FriendEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).getFriendInfoExact(Api.getFriendInfo, keyword));
        }else {
            httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(FriendListEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).getFriendInfo(Api.getFriendInfo, keyword, start, offset));
        }
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            if(start==-1||offset==-1){
                FriendEntity friendEntity=httpClientEntity.getObj();
                userEntity=new FriendListEntity();
                List<FriendEntity> list=new ArrayList<>();
                list.add(friendEntity);
                userEntity.setItems(list);
            }else{
                userEntity=httpClientEntity.getObj();
            }
        }else{
            userEntity=new FriendListEntity();
            userEntity.setCode(httpClientEntity.getCode());
            userEntity.setMsg(httpClientEntity.getMessage());
            userEntity.setRetcode(httpClientEntity.getCode());
        }
        return userEntity;
    }

    @Override
    public CodeEntity dealFriendApply(String apply_id, String apply_state, String refuse_text) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).dealFriendApply(Api.dealFriendApply, apply_id,apply_state,refuse_text));
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
    public CodeEntity sendAddFriendApply(String proposer_id, String target_id, String refuse_text, String apply_from) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).sendAddAFriendpply(Api.sendAddAFriendpply, proposer_id,target_id,refuse_text,apply_from));
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
    public List<FriendApplyEntity> getAllFriendApply(String userId, int start, int offset) {
        FriendApplyListEntity friendApplyListEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(FriendApplyListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).getFriendApplyList(Api.getFriendApplyList, userId,start,offset));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            friendApplyListEntity=httpClientEntity.getObj();
        }else{
            friendApplyListEntity=new FriendApplyListEntity();
            friendApplyListEntity.setCode(httpClientEntity.getCode());
            friendApplyListEntity.setMsg(httpClientEntity.getMessage());
            friendApplyListEntity.setRetcode(httpClientEntity.getCode());
            friendApplyListEntity.setData(new ArrayList<FriendApplyEntity>());
        }
        return friendApplyListEntity.getData();
    }

    @Override
    public OnLineEntity checkUserOnlineStatu(String userId) {
        OnLineEntity onLineEntity=null;
        HttpClientEntity httpClient= (HttpClientEntity) HttpClient.getInstance().sends(OnLineEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).checkOnline(Api.checkOnline,userId));
        if(httpClient.getCode()==Constan.NET_SUCCESS){
            onLineEntity=httpClient.getObj();
        }else{
            onLineEntity=new OnLineEntity();
            onLineEntity.setCode(httpClient.getCode());
//            onLineEntity.setMsg(httpClient.getMessage());
//            onLineEntity.setRetcode(httpClient.getCode());
        }
        return onLineEntity;
    }

    @Override
    public CodeEntity changeFriendInfo(int userId, String note, int friendId) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).updateUserName(Api.updateUserName,userId,note,friendId));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setCode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity updateUserInfo(String nickname,int sex,String birthday,String area,String mySign,String headImg) {
//        LogUtil.LogDebug("ggyy",birthday );
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).updateUserInfo(Api.updateUserInfo,nickname,sex,birthday,area,mySign,headImg));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            return (CodeEntity)httpClientEntity.getObj();
        }else{
            UserEntity userEntity2=new UserEntity();
            userEntity2.setCode(httpClientEntity.getCode());
            userEntity2.setRetcode(httpClientEntity.getCode());
            userEntity2.setMsg(httpClientEntity.getMessage());
            return  userEntity2;
        }
    }

    @Override
    public CodeEntity updateFriendRelation(String targetId, String type, String relation, String black) {
        CodeEntity codeEntity=null;
        switch (Integer.valueOf(type)){
            case 1: //删除
                codeEntity=deleteFriend(targetId,type);
                break;
            case 2: //拉黑   取消拉黑
                if(black==null){
                    codeEntity=unBlockFriend(targetId,type);
                }else{
                    codeEntity=blockFriend(targetId,type,black);
                }
                break;
            case 3: //屏蔽   取消屏蔽
                codeEntity=shieldFriend(type,targetId,relation);
                break;
        }
        return codeEntity;
    }

    @Override
    public CodeEntity blockFriend(String targetId, String type, String black) {
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).blockFriendRelation(Api.updateFriendRelation,targetId,type,black));
        CodeEntity codeEntity=httpClientEntity.getObj();
        return codeEntity;
    }

    @Override
    public CodeEntity unBlockFriend(String targetId, String type) {
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).unBlockFriendRelation(Api.updateFriendRelation,targetId,type));
        CodeEntity codeEntity=httpClientEntity.getObj();
        return codeEntity;
    }

    @Override
    public CodeEntity deleteFriend(String targetId, String type) {
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).deleteFriendRelation(Api.updateFriendRelation,type,targetId));
        CodeEntity codeEntity=httpClientEntity.getObj();
        return codeEntity;
    }

    @Override
    public CodeEntity shieldFriend(String type, String targetId, String relation) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).shieldFriendRelation(Api.updateFriendRelation,targetId,type,relation));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS) {
            codeEntity = httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
        }
        return codeEntity;
    }

    @Override
    public Address getArea(String userId) {
        Address address=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(Address.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).getArea(Api.GET_AREA,userId));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            address=httpClientEntity.getObj();
        }else{
            address=new Address();
            address.setCode(httpClientEntity.getCode());
            address.setMsg(httpClientEntity.getMessage());
            address.setRetcode(httpClientEntity.getCode());
        }
        return address;
    }


    @Override
    public CodeEntity addFriend(String targetId) {
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance()
                .sends(CodeEntity.class,HttpClient.getInstance()
                        .retrofit().create(RetrofitService.class)
                        .addFriend(Api.addFriend,targetId));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            return (CodeEntity)httpClientEntity.getObj();
        }else{
            UserEntity userEntity2=new UserEntity();
            userEntity2.setCode(httpClientEntity.getCode());
            userEntity2.setRetcode(httpClientEntity.getCode());
            userEntity2.setMsg(httpClientEntity.getMessage());
            return  userEntity2;
        }
    }

    @Override
    public BankCardListEntity getBank(String userId) {
        BankCardListEntity bankCardListEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(BankCardListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).getBank(Api.getBank,userId));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            bankCardListEntity=httpClientEntity.getObj();
        }else{
            bankCardListEntity=new BankCardListEntity();
            bankCardListEntity.setCode(httpClientEntity.getCode());
            bankCardListEntity.setMsg(httpClientEntity.getMessage());
            bankCardListEntity.setRetcode(httpClientEntity.getCode());
        }
        return bankCardListEntity;
    }

    @Override
    public CodeEntity bindBank(int bankId, String bankNo, String bankBranch) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).bindBank(Api.bindBank,bankId,bankNo,bankBranch));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity = new CodeEntity();
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity unbindUserBank(int bankId) {
        CodeEntity codeEntity=null;
        Log.e("test","OkHttp====Message:  bankId-->"+bankId);
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).unbindUserBank(Api.unbindUserBank,bankId));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity = new CodeEntity();
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public BankCardListEntity getUserBank(int type) {
        BankCardListEntity bankCardListEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(BankCardListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).getUserBank(Api.getUserBank,type));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            bankCardListEntity=httpClientEntity.getObj();
        }else{
            bankCardListEntity=new BankCardListEntity();
            bankCardListEntity.setCode(httpClientEntity.getCode());
            bankCardListEntity.setMsg(httpClientEntity.getMessage());
            bankCardListEntity.setRetcode(httpClientEntity.getCode());
        }
        return bankCardListEntity;
    }

    @Override
    public InviteCodeEntity getUserInviteCode() {
        InviteCodeEntity bankCardListEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(InviteCodeEntity.class,"data", HttpClient.getInstance().retrofit().create(RetrofitService.class).getUserInviteCode(Api.getUserInviteCode));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            bankCardListEntity=httpClientEntity.getObj();
        }else{
            bankCardListEntity=new InviteCodeEntity();
            bankCardListEntity.setCode(httpClientEntity.getCode());
            bankCardListEntity.setMsg(httpClientEntity.getMessage());
            bankCardListEntity.setRetcode(httpClientEntity.getCode());
        }
        return bankCardListEntity;
    }

    @Override
    public ShakyStatuEntity getSweetsActivity() {
        ShakyStatuEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(ShakyStatuEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).getSweetsActivity(Api.getSweetsActivity));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new ShakyStatuEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public ShakyCandyEntity insertActivityRecord() {
        ShakyCandyEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(ShakyCandyEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).insertActivityRecord(Api.insertActivityRecord));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new ShakyCandyEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public ShakyCandyEntity getActivityRecord() {
        ShakyCandyEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(ShakyCandyEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).getActivityRecord(Api.getActivityRecord));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new ShakyCandyEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public AddressTeamListEntity queryTeamMembers(String currentPage, String pageSize) {
        AddressTeamListEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(AddressTeamListEntity.class,"data", HttpClient.getInstance().retrofit().create(RetrofitService.class).queryTeamMembers(Api.queryTeamMembers, currentPage, pageSize));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS){
            entity = httpClientEntity.getObj();
        }else {
            entity = new AddressTeamListEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public void saveOrUpdateFriendEntity(FriendEntity friendEntity) {
        CachePool.getInstance().friend().put(friendEntity);
    }

    @Override
    public FriendEntity findFriendById(long friendId) {
        return CachePool.getInstance().friend().get(friendId);
    }

    @Override
    public List<FriendEntity> findAllFriendList() {
        return CachePool.getInstance().friend().getAll();
    }

    @Override
    public void saveOrUpdateFriendList(List<FriendEntity> list) {
        CachePool.getInstance().friend().put(list);
    }
}