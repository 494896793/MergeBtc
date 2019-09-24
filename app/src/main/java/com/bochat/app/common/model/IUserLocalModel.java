package com.bochat.app.common.model;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.UserEntity;

import java.util.List;

/**
 * 2019/4/28
 * Author LDL
 **/
public interface IUserLocalModel {


    /*获取本地好友列表*/
    public List<FriendEntity> findAllFriendList();

    /*好友列表本地化*/
    public void saveOrUpdateFriendList(List<FriendEntity> list);

    /*数据库中查询用户信息*/
    public UserEntity getLocalUserInfo();

    /*单个好友本地化保存或更新*/
    public void saveOrUpdateFriendEntity(FriendEntity friendEntity);

    /*根据好友id查询好友信息*/
    public FriendEntity findFriendById(long friendId);




}
